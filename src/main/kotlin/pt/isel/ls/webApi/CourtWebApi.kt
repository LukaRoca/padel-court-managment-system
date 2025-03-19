package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.Token
import pt.isel.ls.webApi.dto.CourtInput
import pt.isel.ls.webApi.dto.CourtOutput
import pt.isel.ls.webServices.ClubServices
import pt.isel.ls.webServices.CourtServices
import pt.isel.ls.webServices.UserServices

class CourtWebApi(private val courtServices: CourtServices) : WebApiExceptions() {

    private fun handleError(e: Exception): Response = httpException(e)

    fun createCourt(request: Request): Response = useWithException {
        val token = request.header("Authorization")?.removePrefix("Bearer ")
            ?: return handleError(AuthorizationException("Missing or invalid token"))
        if (UserServices.getUserByToken(Token(token)) == null) {
            return handleError(AuthorizationException("Invalid token"))
        }
        val courtDto = Json.decodeFromString<CourtInput>(request.bodyString())
        val court = courtServices.createCourt(Name(courtDto.name), Id(courtDto.id))
        Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(CourtOutput(court.id)))
    }

    fun getCourtById(request: Request): Response = useWithException {
        val crid = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid court ID")
        val court = courtServices.getCourtById(Id(crid)) ?: throw NoSuchElementException("Court not found")
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(court))
    }

    fun getCourtsByClub(request: Request): Response = useWithException {
        val clubId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid club ID")
        val club = ClubServices.getClubById(Id(clubId)) ?: throw NoSuchElementException("Club not found")
        val courts = CourtServices.getCourtsByClub(club)
        if (courts.isNotEmpty()) {
            Response(OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(courts))
        } else {
            throw NoSuchElementException("No courts found for this club")
        }
    }

    val appCourts = routes(
        "courts" bind Method.POST to ::createCourt,
        "courts/{id}" bind Method.GET to ::getCourtById,
        "clubs/{id}/courts" bind Method.GET to ::getCourtsByClub
    )

}

