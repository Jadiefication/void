package main.java.main.ClientHandler;

import main.java.main.HTTP.Request.RequestDTO;
import main.java.main.HTTP.Response.ResponseDTO;
import main.java.main.HTTP.Builder.HttpResponseBuilder;
import main.java.main.HTTP.Parser.HTTPRequestParser;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.function.BiConsumer;

import static main.java.main.Server.Server.router;

/**
 * The ClientHandler class is responsible for handling individual client connections.
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    /**
     * Constructs a new ClientHandler with the given client socket.
     *
     * @param socket The client socket to handle.
     */
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    /**
     * Handles the client connection by processing the request and sending a response.
     */
    @Override
    public void run() {
        try {
            RequestDTO request = HTTPRequestParser.parse(clientSocket.getInputStream());
            ResponseDTO response = new ResponseDTO();

            BiConsumer<RequestDTO, ResponseDTO> handler = router.getHandler(request.getMethod(), request.getPath());

            if (handler != null) {
                handler.accept(request, response);
            } else {
                response.setStatusCode(404);
                response.setBody("Not Found");
                response.setHeaders(Map.of("Content-Type", "text/html"));
            }

            HttpResponseBuilder.build(response, clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Failed to close the socket: " + e.getMessage());
            }
        }
    }
}
