package main.java.main.ClientHandler

import main.Router.Router
import main.WebSocket.WebSocketClient
import main.java.main.HTTP.Parser.HTTPParser
import java.net.Socket
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class ClientHandler(private val client: Socket): Thread() {

    companion object {
        lateinit var webSocketClient: WebSocketClient

        fun getStoredClient(clientId: String): WebSocketClient? {
            val clientsData = Files.readAllLines(Paths.get("clients.txt"))
            return clientsData
                .find { it.split(",")[0] == clientId }
                ?.let { WebSocketClient() }
        }
    }

    override fun run() {
        currentThread().name = "ClientHandler-${client.port}"
        webSocketClient = WebSocketClient()
        webSocketClient.connect()
        writeConnection(webSocketClient)
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

    fun writeConnection(client: WebSocketClient) {
        val clientData = mapOf(
            "id" to client,
            "timestamp" to System.currentTimeMillis().toString()
        )

        Files.write(
            Paths.get("clients.txt"),
            "${clientData["id"]},${clientData["timestamp"]}\n".toByteArray(),
            StandardOpenOption.APPEND,
            StandardOpenOption.CREATE
        )
    }
}