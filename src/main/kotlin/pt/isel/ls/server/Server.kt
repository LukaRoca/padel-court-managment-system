package pt.isel.ls.server

import org.http4k.routing.ResourceLoader
import org.http4k.routing.routes
import org.http4k.routing.singlePageApp
import org.http4k.server.*
import org.postgresql.ds.PGSimpleDataSource
import org.slf4j.LoggerFactory
import pt.isel.ls.Routes
import pt.isel.ls.storage.dataPostgres.*
import pt.isel.ls.webApi.*
import pt.isel.ls.webServices.*


private val logger = LoggerFactory.getLogger("HTTPServer")

fun main(){

    val dataSource = PGSimpleDataSource()
    val jdbcDatabaseURL = System.getenv("JDBC_DATABASE_URL")
    //dataSource.setURL(jdbcDatabaseURL)
    //dataSource.setURL("jdbc:postgresql://localhost/ls?user=postgres&password=tubarao")
    dataSource.setURL("jdbc:postgresql://localhost/postgres?user=postgres&password=tubarao")

    val data = DataPostgres(dataSource)
    val services = IServices(data)
    val webApi = WebApi(services)

    val jettyServer = Routes(webApi).app.asServer(Jetty(8080)).start()
    logger.info("server started")

    readln()
    jettyServer.stop()
    logger.info("server stopped")

}