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
import pt.isel.ls.domain.Club
import pt.isel.ls.dto.ClubDTO
import pt.isel.ls.dto.ResponseClubDto
import pt.isel.ls.dto.ResponseUserDto
import pt.isel.ls.dto.UserDTO
import pt.isel.ls.webServices.ClubServices
import pt.isel.ls.webServices.UserServices

class clubWebApi(private val ClubServices: ClubServices) {
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

    fun createClub(request: Request): Response {
        logRequest(request)
        val token = request.header("Authorization")?.removePrefix("Bearer ")
            ?: return Response(Status.UNAUTHORIZED)
                .body(Json.encodeToString(mapOf("error" to "Missing or invalid token")))
        val clubData = Json.decodeFromString<ClubDTO>(request.bodyString())

        val club = ClubServices.createClub(clubData.name, token)
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