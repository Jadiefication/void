package main.java.main.HTTP.Builder;

import main.java.main.HTTP.Response.ResponseDTO;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * The HttpResponseBuilder class is responsible for building HTTP responses.
 */
public class HttpResponseBuilder {

    /**
     * Builds an HTTP response based on the given ResponseDTO and writes it to the output stream.
     *
     * @param responseDTO The ResponseDTO containing the response details.
     * @param outputStream The output stream to write the response to.
     * @throws IOException If an I/O error occurs while writing to the output stream.
     */
    public static void build(ResponseDTO responseDTO, OutputStream outputStream) throws IOException {
        PrintWriter writer = new PrintWriter(outputStream, true);
        writer.println("HTTP/1.1 " + responseDTO.getStatusCode() + " " + responseDTO.getStatusMessage());
        for (Map.Entry<String, String> header : responseDTO.getHeaders().entrySet()) {
            writer.println(header.getKey() + ": " + header.getValue());
        }
        writer.println();
        writer.println(responseDTO.getBody());
    }
}
