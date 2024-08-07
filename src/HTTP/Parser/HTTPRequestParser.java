package HTTP.Parser;

import HTTP.Request.RequestDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequestParser {
    public static RequestDTO parse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        String[] requestLine = line.split(" ");

        String method = requestLine[0];
        String path = requestLine[1];

        Map<String, String> headers = new HashMap<>();
        while (!(line = reader.readLine()).isEmpty()) {
            String[] header = line.split(": ");
            headers.put(header[0], header[1]);
        }

        StringBuilder body = new StringBuilder();
        while (reader.ready()) {
            body.append((char) reader.read());
        }

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setMethod(method);
        requestDTO.setPath(path);
        requestDTO.setHeaders(headers);
        requestDTO.setBody(body.toString());

        return requestDTO;
    }
}
