package HTTP.Builder;

import HTTP.Response.ResponseDTO;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public class HttpResponseBuilder {
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
