package main.Routing.Router;

import main.HTTP.Request.RequestDTO;
import main.HTTP.Response.ResponseDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * The Router class manages routes and their corresponding handlers.
 */
public class Router {
    private final Map<String, Map<String, BiConsumer<RequestDTO, ResponseDTO>>> routes = new HashMap<>();

    /**
     * Adds a new route with the specified method, path, and handler.
     *
     * @param method The HTTP method for the route.
     * @param path The path for the route.
     * @param handler The handler function for the route.
     */
    public void addRoute(String method, String path, BiConsumer<RequestDTO, ResponseDTO> handler) {
        routes.computeIfAbsent(path, k -> new HashMap<>()).put(method, handler);
    }

    /**
     * Retrieves the handler for the specified method and path.
     *
     * @param method The HTTP method.
     * @param path The path.
     * @return The handler function for the specified method and path, or null if not found.
     */
    public BiConsumer<RequestDTO, ResponseDTO> getHandler(String method, String path) {
        Map<String, BiConsumer<RequestDTO, ResponseDTO>> methodMap = routes.get(path);
        if (methodMap != null) {
            return methodMap.get(method);
        }
        return null;
    }
}