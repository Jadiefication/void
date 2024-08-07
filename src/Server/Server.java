package Server;

import ClientHandler.ClientHandler;
import Metadata.Metadata;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

    public static void MakeServer(int port) {
        try(ServerSocket server = new ServerSocket(port)) {
            server.setSoTimeout(4000);
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

    public static void MakePage(Metadata metadata, java.net.ServerSocket server, String html) {

    }
}
