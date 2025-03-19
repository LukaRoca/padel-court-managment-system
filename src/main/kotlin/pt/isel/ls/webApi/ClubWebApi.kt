package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.eclipse.jetty.websocket.core.CoreSession.Empty
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.slf4j.LoggerFactory
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.webApi.dto.ClubDTO
import pt.isel.ls.webApi.dto.ResponseClubDto
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

        val club = clubServices.createClub(Name(clubData.name), token)
            ?: return Response(Status.UNAUTHORIZED)
                .body(Json.encodeToString(mapOf("error" to "Invalid token")))

        return Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(ResponseClubDto(club.id.id)))

    }

    private fun getClubById(request: Request): Response {
        logRequest(request)
        val clubId = request.path("id")?.toIntOrNull()
        if (clubId == null) {
            return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid club ID")))
        }

        val club = ClubServices.getClubById(Id(clubId))
        return if (club != null) {
            Response(OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(club))
        } else {
            Response(Status.NOT_FOUND)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "User not found")))
        }
    }

    private fun getClubs(request: Request): Response {
        logRequest(request)
        val clubs = clubServices.getClubs()
        return if (clubs is Empty) {
            Response(Status.NOT_FOUND)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "List is Empty")))
        } else {
            Response(OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(clubs))
        }
    }

    val appClubs = routes(
        "club" bind Method.POST to ::createClub,
        "clubs/{id}" bind Method.GET to ::getClubById,
        "clubs" bind Method.GET to ::getClubs,

    )
}