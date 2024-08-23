package main.java.main.HTTP.Response;

import java.util.Map;

/**
 * The ResponseDTO class represents an HTTP response.
 */
public class ResponseDTO {
    private int statusCode;
    private String statusMessage;
    private Map<String, String> headers;
    private String body;

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
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
