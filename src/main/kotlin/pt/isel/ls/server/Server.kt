package pt.isel.ls.server

import org.http4k.routing.routes
import org.http4k.server.*
import org.slf4j.LoggerFactory
import pt.isel.ls.webApi.*
import pt.isel.ls.webServices.*


private val logger = LoggerFactory.getLogger("HTTPServer")

fun main(){

    val userService = UserServices
    val clubService = ClubServices
    val courtService = CourtServices
    val rentalService = RentalServices


    val userWebApi = UserWebApi(userService)
    val clubWebApi = ClubWebApi(clubService)
    val courtWebApi = CourtWebApi(courtService)
    val rentalWebApi = RentalWebApi(rentalService)


    val appRoutes = routes(
        userWebApi.app,
        clubWebApi.appClubs,
        courtWebApi.appCourts,
        rentalWebApi.appRental
    )

    //val jettyServerLuka = appRoutes.asServer(Jetty(8082)).start()
    val jettyServerAfonso = appRoutes.asServer(Jetty(8081)).start()

    //val jettyServer = appRoutes.asServer(Jetty(8080)).start()
    logger.info("server started")

    readln()
    //jettyServer.stop()
    //jettyServerLuka.stop()
    jettyServerAfonso.stop()
    logger.info("server stopped")

}