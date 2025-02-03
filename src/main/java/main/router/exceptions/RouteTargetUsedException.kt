package main.router.exceptions

class RouteTargetUsedException: Exception("Specified target location is already used in a different route") {
}