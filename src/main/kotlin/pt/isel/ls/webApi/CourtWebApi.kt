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
import pt.isel.ls.webApi.dto.CourtInput
import pt.isel.ls.webApi.dto.CourtOutput
import pt.isel.ls.webServices.CourtServices

class CourtWebApi(private val courtServices: CourtServices) : WebApiExceptions() {
    private fun handleError(e: Exception): Response = httpException(e)

    fun createCourt(request: Request): Response = try {
        val courtDto = Json.decodeFromString<CourtInput>(request.bodyString())
        val court = courtServices.createCourt(Name(courtDto.name), Id(courtDto.cid)) ?: throw NoSuchElementException()
        Response(CREATED).json(CourtOutput(court.id))
    } catch (e: Exception) {
        handleError(e)
    }
    fun getCourtById(request: Request): Response = try {
        val crid = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid court ID")
        val court = courtServices.getCourtById(Id(crid)) ?: throw NoSuchElementException()
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(court))
    } catch (e: Exception) {
        handleError(e)
    }
    fun getCourtsByClub(request: Request): Response = try {
        val clubId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid club ID")
        val courts = courtServices.getCourtsByClub(Id(clubId)) ?: emptyList()
        if (courts.isNotEmpty()) {
            Response(OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(courts))
        } else {
            throw NoSuchElementException()
        }
    } catch (e: Exception) {
        handleError(e)
    }
    val appCourts = routes(
        "courts" bind Method.POST to ::createCourt,
        "courts/{id}" bind Method.GET to ::getCourtById,
        "clubs/{id}/courts" bind Method.GET to ::getCourtsByClub
    )
}