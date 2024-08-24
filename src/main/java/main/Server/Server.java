package main.java.main.Server;
import main.java.main.ClientHandler.ClientHandler;
import main.java.main.Html.Page.PageProcessor.PageReflector;
import main.java.main.Routing.Router.Router;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * The Server class is responsible for creating and managing a server socket.
 */
public class Server {

    public static Router router = new Router();

    /**
     * Creates and starts a server on the specified port.
     *
     * @param port The port number on which the server will listen.
     * @param packageName The package with your routes.
     */
    public static void MakeServer(int port, String packageName) {
        pageThread(packageName).start();
        try(ServerSocket server = new ServerSocket(port)) {
            while(true) {
                try {
                    Socket socket = server.accept();
                    new Thread(new ClientHandler(socket)).start();
                } catch (SocketTimeoutException e) {
                    System.out.println("Socket timed out waiting for a connection.");
                } catch (IOException e) {
                    System.out.println("I/O error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to start the server: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static Thread pageThread(String packageName) {
        return new Thread(() -> {
            PageReflector.processClasses(packageName);
        });
    }

    /**
     * Creates a page with the given method, path and HTML content.
     *
     * @param method The HTTP method for the route (e.g., GET, POST).
     * @param path The URL path for the route.
     * @param html The HTML content of the page.
     * @param headers The headers for the page.
     */
    public static void MakePage(String method, String path, String html, Map<String, String> headers) {
        router.addRoute(method, path, (_, res) -> {
            res.setStatusCode(200);
            res.setStatusMessage("OK");
            res.setHeaders(headers);
            res.setBody(html);
        });
    }
}
