package main.java.main.Logger

import main.WebSocket.WebSocketClient
import main.java.main.ClientHandler.ClientHandler
import main.java.main.Server.Server

class Logger {

    companion object {
        fun warn(message: String) {
            Server.webSocket.broadcast("$message" + "WARN")
        }

        fun log(message: String) {
            Server.webSocket.broadcast(message)
        }

        fun error(message: String) {
            Server.webSocket.broadcast("$message" + "ERROR")
        }

        fun alert(message: String) {
            Server.webSocket.broadcast("$message" + "ALERT")
        }

        fun getClient(clientId: String): WebSocketClient? {
            return ClientHandler.getStoredClient(clientId)
        }

        fun warn(message: String, clientId: String) {
            getClient(clientId)?.let { client ->
                Server.webSocket.broadcast("$message" + "WARN", listOf(client))
            }
        }

        fun log(message: String, clientId: String) {
            getClient(clientId)?.let { client ->
                Server.webSocket.broadcast("$message", listOf(client))
            }
        }

        fun error(message: String, clientId: String) {
            getClient(clientId)?.let { client ->
                Server.webSocket.broadcast("$message" + "ERROR", listOf(client))
            }
        }

        fun alert(message: String, clientId: String) {
            getClient(clientId)?.let { client ->
                Server.webSocket.broadcast("$message" + "ALERT", listOf(client))
            }
        }
    }
}