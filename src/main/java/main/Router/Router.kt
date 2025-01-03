package main.Router

import main.java.main.DTO.RequestDTO
import main.java.main.DTO.ResponseDTO
import main.java.main.HTTP.Builder.HTTPBuilder
import java.net.Socket

class Router {

    val routes = mutableMapOf<String, String>()

    fun route(requestDTO: RequestDTO, client: Socket) {
        val js = "const ws = new WebSocket('ws://localhost:8081/');ws.addEventListener(\"message\", (event) => {if (event) {const message = event.data;if (message.endsWith('WARN')) {console.warn(message.replace('WARN', '').trim());} else if (message.endsWith('ERROR')) {console.error(message.replace('ERROR', '').trim());} else if (message.endsWith('ALERT')) {window.alert(message.replace('ALERT', '').trim());} else {console.log(message);}}});"
        val target = requestDTO.target
        if (routes.containsKey(target)) {
            HTTPBuilder().build(ResponseDTO(200,
                "All is well",
                mapOf(
                    Pair("Content-Type", "text/html"),
                    Pair("Upgrade", "websocket"),
                    Pair("Connection", "Upgrade")
                ),
                "<html><body><h1>No Route Found!</h1><script>$js</script></body></html>"),
                client.getOutputStream())
        } else {
            HTTPBuilder().build(
                ResponseDTO(404,
                "Not Found",
                mapOf(
                    Pair("Content-Type", "text/html"),
                    Pair("Upgrade", "websocket"),
                    Pair("Connection", "Upgrade")
                ),
                "<html><body><h1>No Route Found!</h1><script>$js</script></body></html>"),
                client.getOutputStream())
        }
    }
}