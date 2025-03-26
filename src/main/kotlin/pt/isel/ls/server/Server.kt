package pt.isel.ls.server

import org.http4k.routing.routes
import org.http4k.server.*
import org.postgresql.ds.PGSimpleDataSource
import org.slf4j.LoggerFactory
import pt.isel.ls.storage.dataMem.ClubDataMem
import pt.isel.ls.storage.dataMem.CourtDataMem
import pt.isel.ls.storage.dataMem.RentalDataMem
import pt.isel.ls.storage.dataMem.UserDataMem
import pt.isel.ls.storage.dataPostgres.UserDataPostgres
import pt.isel.ls.webApi.*
import pt.isel.ls.webServices.*


private val logger = LoggerFactory.getLogger("HTTPServer")

fun main(){

    val dataSource = PGSimpleDataSource()
    val jdbcDatabaseURL = System.getenv("JDBC_DATABASE_URL")
    dataSource.setURL(jdbcDatabaseURL)


    val userService = UserServices(UserDataPostgres(dataSource.connection))
    val clubService = ClubServices(ClubDataMem)
    val rentalService = RentalServices(RentalDataMem)
    val courtService = CourtServices(CourtDataMem)

    val userWebApi = UserWebApi(userService)
    val clubWebApi = ClubWebApi(clubService)
    val rentalWebApi = RentalWebApi(rentalService)
    val courtWebApi = CourtWebApi(courtService)

    val appRoutes = routes(
        userWebApi.app,
        clubWebApi.appClubs,
        rentalWebApi.appRental,
        courtWebApi.appCourts
    )

    //val jettyServerLuka = appRoutes.asServer(Jetty(8082)).start()
    //val jettyServerAfonso = appRoutes.asServer(Jetty(8081)).start()

    val jettyServer = appRoutes.asServer(Jetty(8080)).start()
    logger.info("server started")

    readln()
    jettyServer.stop()
    //jettyServerLuka.stop()
    //jettyServerAfonso.stop()
    logger.info("server stopped")

}