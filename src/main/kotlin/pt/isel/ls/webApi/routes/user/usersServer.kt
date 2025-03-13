package pt.isel.ls.webApi.routes.user

import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import pt.isel.ls.domain.User

private val logger = LoggerFactory.getLogger("HTTPServer")

fun main(){

    val userService = UserServices

    val userWebApi = UserWebApi(userService)

    val app = userWebApi.app

    val jettyServer = app.asServer(Jetty(8080)).start()
    logger.info("server started")

    readln()
    jettyServer.stop()
    logger.info("server stopped")

}