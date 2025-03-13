package pt.isel.ls.webApi.routes.club

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.slf4j.LoggerFactory
import pt.isel.ls.webServices.clubService

class ClubWebApi(private val clubService: clubService) {
    private val logger = LoggerFactory.getLogger("pt.isel.ls.webApi.routes.club.ClubsRoute")

    fun logRequest(request: Request) {
        logger.info(
            "incoming request: method={}, uri={}, content-type={} accept={}",
            request.method,
            request.uri,
            request.header("content-type"),
            request.header("accept"),
        )
    }
    fun getClubs(request: Request): Response {
        logRequest(request)
        val clubs = clubService.getClubs()
        return Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(clubs))
    }
}