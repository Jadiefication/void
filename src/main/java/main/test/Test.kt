package main.java.main.Test

import main.java.main.Server.Server
import main.router.Route
import main.router.Router
import main.test.routes.home.HomeRoute

fun main() {
    val server = Server("main.test.routes")

    Router.addRoute(HomeRoute())

    server.startServer(8080)
}
