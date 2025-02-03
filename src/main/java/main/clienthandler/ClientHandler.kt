package main.java.main.ClientHandler

import main.router.Router
import main.java.main.HTTP.Parser.HTTPParser
import java.net.Socket
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class ClientHandler(private val client: Socket): Thread() {

    override fun run() {
        currentThread().name = "ClientHandler-${client.port}"
        try {
            val request = HTTPParser().parse(client.getInputStream())
            /*val response = HTTPBuilder().build(ResponseDTO(200,
                "All is well",
                mapOf(
                    Pair("Content-Type", "text/html"),
                    Pair("Upgrade", "websocket"),
                    Pair("Connection", "Upgrade")
                ),
                "<html><body><h1>No Route Found!</h1><script>$js</script></body></html>"),
                client.getOutputStream())*/
            Router().route(request, client)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            client.close()
        }
    }
}