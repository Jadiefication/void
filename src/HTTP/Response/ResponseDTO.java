package HTTP.Response;


import java.util.Map;

public class ResponseDTO {
    private final int statusCode;
    private final String statusMessage;
    private final Map<String, String> headers;
    private final String body;

    public int getStatusCode() {
        return statusCode;
    }

    public ResponseDTO(int statusCode, String statusMessage, Map<String, String> headers, String body) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.headers = headers;
        this.body = body;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
