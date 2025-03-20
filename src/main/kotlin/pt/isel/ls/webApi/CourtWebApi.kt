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
import pt.isel.ls.domain.Name
import pt.isel.ls.webApi.dto.CourtInput
import pt.isel.ls.webApi.dto.CourtOutput
import pt.isel.ls.webServices.CourtServices

class CourtWebApi( private val courtServices: CourtServices) {

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
    fun createCourt(request: Request): Response {
        logRequest(request)

        val token = request.header("Authorization")?.removePrefix("Bearer ")
            ?: return Response(Status.UNAUTHORIZED)
                .body(Json.encodeToString(mapOf("error" to "Missing or invalid token")))

        val courtDto = Json.decodeFromString<CourtInput>(request.bodyString())

        return try {
            val court = courtServices.createCourt(Name(courtDto.name), Id(courtDto.id))
            Response(CREATED)
                .header("content-type", "application/json")
                .body(Json.encodeToString(CourtOutput(court.id)))
        } catch (e: IllegalArgumentException) {
            Response(Status.BAD_REQUEST)
                .body(Json.encodeToString(mapOf("error" to e.message)))
        }
    }


    private fun getCourtById(request: Request): Response {
        logRequest(request)
        val crid = request.path("id")?.toIntOrNull()
            ?: return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid user ID")))

        val court = courtServices.getCourtById(Id(crid))
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


    private fun getCourtsByClubId(request: Request): Response {
        logRequest(request)
        val clubId = request.path("id")?.toIntOrNull()
            ?: return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid club ID")))

        val courts = courtServices.getCourtsByClub(Id(clubId))

        return if (courts.isNullOrEmpty()) {
                Response(OK)
                    .header("content-type", "application/json")
                    .body(Json.encodeToString(courts))
            } else {
                Response(Status.NOT_FOUND)
                    .header("content-type", "application/json")
                    .body(Json.encodeToString(mapOf("error" to "No courts found for this club")))
            }
    }
    val appCourts = routes(
        "courts" bind Method.POST to ::createCourt,
        "courts/{id}" bind Method.GET to ::getCourtById,
        "clubs/{id}/courts" bind Method.GET to ::getCourtsByClubId
    )

}

