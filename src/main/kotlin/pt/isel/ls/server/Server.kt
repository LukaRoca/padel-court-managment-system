package pt.isel.ls.server

import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import pt.isel.ls.webApi.UserWebApi
import pt.isel.ls.webApi.ClubWebApi
import pt.isel.ls.webApi.RentalWebApi
import pt.isel.ls.webServices.ClubServices
import pt.isel.ls.webServices.RentalServices
import pt.isel.ls.webServices.UserServices


private val logger = LoggerFactory.getLogger("HTTPServer")

fun main(){

    val userService = UserServices
    val clubService = ClubServices
    val rentalService = RentalServices

    val userWebApi = UserWebApi(userService)
    val clubWebApi = ClubWebApi(clubService)
    val rentalWebApi = RentalWebApi(rentalService)

    val appRoutes = routes(
        userWebApi.app,
        clubWebApi.appClubs,
        rentalWebApi.appRental
    )

    val jettyServer = appRoutes.asServer(Jetty(8080)).start()
    logger.info("server started")

    readln()
    jettyServer.stop()
    logger.info("server stopped")

}