package pt.isel.ls

import org.http4k.server.*
import pt.isel.ls.data.dataPostgres.*
import pt.isel.ls.webApi.*
import pt.isel.ls.webServices.*

fun main(){

    /*
    Setting the data src without needing a env variable
    val data = DataPostgres(System.getenv("jdbc:postgresql://localhost/ls?user=postgres&password=tubarao"))
    val data = DataPostgres(System.getenv("jdbc:postgresql://localhost/postgres?user=postgres&password=tubarao"))
     */

    val db = DataPostgres(System.getenv(CONN_NAME))
    val webApi = WebApi(IServices(db))

    val jettyServer = Routes(webApi).app.asServer(Jetty(PORT)).start()
    logger.info("server started")
    readln()
    jettyServer.stop()

    logger.info("server stopped")
}