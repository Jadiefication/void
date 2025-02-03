package main.router

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Route(val target: String = "/", val type: String = "GET")

