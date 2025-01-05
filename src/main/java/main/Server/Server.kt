package main.java.main.Server

import main.java.main.ClientHandler.ClientHandler
import main.java.main.Logger.Logger
import main.java.main.WebSocket.WebSocketServer
import java.io.File
import java.io.IOException
import java.net.ServerSocket

class Server {

    companion object {
        var webSocket = WebSocketServer()
        lateinit var fileLocation: String
    }

    constructor(location: String = "") {
        fileLocation = location + "clients.txt"
        try {
            val file = File(location + "clients.txt")
            if (!file.exists()) {
                file.createNewFile()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private lateinit var socket: ServerSocket

    init {
        Thread {
            while (true) {
                print("> ")
                extractMessage(readln())
            }
        }.start()
    }

    fun extractMessage(input: String) {

        val errorPattern = """ERROR\((.*?)\)""".toRegex()
        val warnPattern = """WARN\((.*?)\)""".toRegex()
        val logPattern = """LOG\((.*?)\)""".toRegex()
        val alertPattern = """ALERT\((.*?)\)""".toRegex()

        if (errorPattern.containsMatchIn(input)) {
            errorPattern.find(input)?.let { match ->
                val message = match.groupValues[1] // Gets content inside parentheses
                Logger.error(message)
            }
        } else if (warnPattern.containsMatchIn(input)) {
            warnPattern.find(input)?.let { match ->
                val message = match.groupValues[1] // Gets content inside parentheses
                Logger.warn(message)
            }
        } else if (alertPattern.containsMatchIn(input)) {
            alertPattern.find(input)?.let { match ->
                val message = match.groupValues[1] // Gets content inside parentheses
                Logger.alert(message)
            }
        } else if (logPattern.containsMatchIn(input)) {
            logPattern.find(input)?.let { match ->
                val message = match.groupValues[1] // Gets content inside parentheses
                Logger.log(message)
            }
        } else {
            println("\u001B[1m\u001B[31mInvalid input\u001B[0m")

        }
    }


    fun startServer(port: Int) {
        Thread {
            try {
                socket = ServerSocket(port)
                webSocket.start()
                while (socket.isBound) {
                    val client = socket.accept()
                    Thread(ClientHandler(client)).start()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                socket.close()
                webSocket.connections.forEach {
                    it.close()
                }
                webSocket.stop()
            }
        }.start()
    }
}