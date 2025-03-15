package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.CREATED
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.slf4j.LoggerFactory
import pt.isel.ls.dto.ClubDTO
import pt.isel.ls.dto.ResponseClubDto
import pt.isel.ls.webServices.ClubServices

class ClubWebApi(private val clubServices: ClubServices) {
    private val logger = LoggerFactory.getLogger("pt.isel.ls.webApi.routes.club.ClubsRoute")

    private fun logRequest(request: Request) {
        logger.info(
            "incoming request: method={}, uri={}, content-type={} accept={}",
            request.method,
            request.uri,
            request.header("content-type"),
            request.header("accept"),
        )
    }

    private fun createClub(request: Request): Response {
        logRequest(request)
        val token = request.header("Authorization")?.removePrefix("Bearer ")
            ?: return Response(Status.UNAUTHORIZED)
                .body(Json.encodeToString(mapOf("error" to "Missing or invalid token")))
        val clubData = Json.decodeFromString<ClubDTO>(request.bodyString())

        val club = clubServices.createClub(clubData.name, token)
            ?: return Response(Status.UNAUTHORIZED)
                .body(Json.encodeToString(mapOf("error" to "Invalid token")))

        return Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(ResponseClubDto(club.id.id)))

    }

    val appClubs = routes(
        "clubs" bind Method.POST to ::createClub,
    )
}