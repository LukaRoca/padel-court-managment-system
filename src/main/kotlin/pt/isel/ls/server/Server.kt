package pt.isel.ls.server

import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import pt.isel.ls.webApi.UserWebApi
import pt.isel.ls.webApi.ClubWebApi
import pt.isel.ls.webApi.CourtWebApi
import pt.isel.ls.webServices.ClubServices


private val logger = LoggerFactory.getLogger("HTTPServer")

fun main(){
    val clubService = ClubServices

    val userWebApi = UserWebApi()
    val clubWebApi = ClubWebApi(clubService)
    val courtWebApi = CourtWebApi()

    val appRoutes = routes(
        userWebApi.app,
        clubWebApi.appClubs,
        courtWebApi.appCourts
    )

    val jettyServer = appRoutes.asServer(Jetty(8082)).start()
    logger.info("server started")

    readln()
    jettyServer.stop()
    logger.info("server stopped")

}