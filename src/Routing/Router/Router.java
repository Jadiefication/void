package Routing.Router;

import HTTP.Request.RequestDTO;
import HTTP.Response.ResponseDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Router {
    private final Map<String, Map<String, BiConsumer<RequestDTO, ResponseDTO>>> routes = new HashMap<>();

    public void addRoute(String method, String path, BiConsumer<RequestDTO, ResponseDTO> handler) {
        routes.computeIfAbsent(path, k -> new HashMap<>()).put(method, handler);
    }

    public BiConsumer<RequestDTO, ResponseDTO> getHandler(String method, String path) {
        Map<String, BiConsumer<RequestDTO, ResponseDTO>> methodMap = routes.get(path);
        if (methodMap != null) {
            return methodMap.get(method);
        }
        return null;
    }
}
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