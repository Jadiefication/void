package main.java.main.Server

import main.java.main.ClientHandler.ClientHandler
import main.router.Route
import main.router.Router
import java.io.File
import java.io.IOException
import java.net.ServerSocket

class Server {

    companion object {
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


    fun startServer(port: Int) {
        Thread {
            try {
                socket = ServerSocket(port)
                while (socket.isBound) {
                    val client = socket.accept()
                    Thread(ClientHandler(client)).start()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                socket.close()
            }
        }.start()
    }
}