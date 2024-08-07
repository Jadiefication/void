package ClientHandler;

import HTTP.Request.RequestDTO;
import HTTP.Response.ResponseDTO;
import HTTP.Builder.HttpResponseBuilder;
import HTTP.Parser.HTTPRequestParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable{

    private final Socket clientSocket;
    private final String html;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {
            RequestDTO request = HTTPRequestParser.parse(inputStream);
            HttpResponseBuilder.build(new ResponseDTO(200, "OK", Map.of("Content-Type", "text/html"), html), outputStream);
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
