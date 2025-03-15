package pt.isel.ls.server

import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import pt.isel.ls.webApi.UserWebApi
import pt.isel.ls.webApi.clubWebApi
import pt.isel.ls.webServices.ClubServices
import pt.isel.ls.webServices.UserServices


private val logger = LoggerFactory.getLogger("HTTPServer")

fun main(){

    val userService = UserServices
    val clubService = ClubServices

    val userWebApi = UserWebApi(userService)
    val clubWebApi = clubWebApi(clubService)

    val appRoutes = routes(
        userWebApi.app,
        clubWebApi.appClubs
    )

    val jettyServer = appRoutes.asServer(Jetty(8080)).start()
    logger.info("server started")

    readln()
    jettyServer.stop()
    logger.info("server stopped")

}