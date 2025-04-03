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
import pt.isel.ls.webApi.dto.ClubInput
import pt.isel.ls.webApi.dto.ClubOutput
import pt.isel.ls.webServices.ClubServices


class ClubWebApi(private val clubServices: ClubServices) : WebApiExceptions() {
    private fun handleError(e: Exception): Response = httpException(e)

    fun createClub(request: Request): Response = try {
        val token = request.header("Authorization")?.removePrefix("Bearer ")
            ?: throw IllegalArgumentException("Missing or invalid token")
        val clubDto = Json.decodeFromString<ClubInput>(request.bodyString())
        val club = clubServices.createClub(Name(clubDto.name), Token(token)) ?: throw NoSuchElementException()
        Response(CREATED).json(ClubOutput(club.id))
    } catch (e: Exception) {
        handleError(e)
    }

    fun getClubById(request: Request): Response = try {
        val clubId = request.path("id")?.toIntOrNull() ?: throw IllegalArgumentException()
        val club = clubServices.getClubById(Id(clubId)) ?: throw NoSuchElementException()
        Response(OK).json(club)
    } catch (e: Exception) {
        handleError(e)
    }

    fun getClubs(request: Request): Response = try {
        val clubs = clubServices.getClubs()
        if (clubs.isEmpty()) throw NoSuchElementException()
        Response(OK).json(clubs)
    } catch (e: Exception) {
        handleError(e)
    }

    val appClubs = routes(
        "club" bind Method.POST to ::createClub,
        "clubs/{id}" bind Method.GET to ::getClubById,
        "clubs" bind Method.GET to ::getClubs,
        )
}




