package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
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
import pt.isel.ls.dto.CourtDTO
import pt.isel.ls.dto.ResponseCourtDto
import pt.isel.ls.webServices.ClubServices
import pt.isel.ls.webServices.CourtServices
import pt.isel.ls.webServices.UserServices

class CourtWebApi {

    private val logger = LoggerFactory.getLogger("pt.isel.ls.webApi.routes.user.UserRoute")

    private fun logRequest(request: Request) {
        logger.info(
            "incoming request: method={}, uri={}, content-type={} accept={}",
            request.method,
            request.uri,
            request.header("content-type"),
            request.header("accept"),
        )
    }
    private fun createCourt(request: Request): Response {
        logRequest(request)
        val token = request.header("Authorization")?.removePrefix("Bearer ")
            ?: return Response(Status.UNAUTHORIZED)
                .body(Json.encodeToString(mapOf("error" to "Missing or invalid token")))
        if (UserServices.getUserByToken(token) == null) {
            return Response(Status.UNAUTHORIZED)
                .body(Json.encodeToString(mapOf("error" to "Invalid token")))
        }
        val courtDto = Json.decodeFromString<CourtDTO>(request.bodyString())
        return try {
            val court = CourtServices.createCourt(courtDto.name, Id(courtDto.id)) // Apenas chamamos o Service
            Response(CREATED)
                .header("content-type", "application/json")
                .body(Json.encodeToString(ResponseCourtDto(court.id.id)))
        } catch (e: IllegalArgumentException) {
            Response(Status.BAD_REQUEST)
                .body(Json.encodeToString(mapOf("error" to e.message)))
        }
    }


    private fun getCourtById(request: Request): Response {
        val crid = request.path("id")?.toIntOrNull()
            ?: return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid user ID")))

        val court = CourtServices.getCourt(crid)
        return if (court != null) {
            Response(OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(court))
        } else {
            Response(Status.NOT_FOUND)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Court not found")))
        }
    }


    private fun getCourtsByClub(request: Request): Response {
        logRequest(request)
        val clubId = request.path("id")?.toIntOrNull()
            ?: return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid club ID")))

        val club = ClubServices.getClubById(clubId)
        return if (club != null) {
            val courts = CourtServices.getCourtsByClub(club)
            if (courts.isNotEmpty()) {
                Response(OK)
                    .header("content-type", "application/json")
                    .body(Json.encodeToString(courts))
            } else {
                Response(Status.NOT_FOUND)
                    .header("content-type", "application/json")
                    .body(Json.encodeToString(mapOf("error" to "No courts found for this club")))
            }
        } else {
            Response(Status.NOT_FOUND)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Club not found")))
        }
    }
    val appCourts = routes(
        "courts" bind Method.POST to ::createCourt,
        "courts/{id}" bind Method.GET to ::getCourtById,
        "clubs/{id}/courts" bind Method.GET to ::getCourtsByClub
    )

}