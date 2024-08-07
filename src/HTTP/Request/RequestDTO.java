package HTTP.Request;

import java.util.Map;

public class RequestDTO {

    private String method;
    private String path;
    private Map<String, String> headers;
    private String body;

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
