package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.Token
import pt.isel.ls.webApi.dto.ClubInput
import pt.isel.ls.webApi.dto.ClubOutput
import pt.isel.ls.webServices.ClubServices


class ClubWebApi(private val services: ClubServices) : WebApiExceptions() {

    fun createClub(request: Request): Response = useWithException {
        val token = request.header("Authorization")?.removePrefix("Bearer ")
            ?: throw IllegalArgumentException("Missing or invalid token")
        val clubInput = Json.decodeFromString<ClubInput>(request.bodyString())
        val club = services.createClub(Name(clubInput.name), Token(token))
            ?: throw IllegalArgumentException("Invalid token")
        Response(Status.CREATED).json(ClubOutput(club.id))
    }

    fun getClubById(request: Request): Response = useWithException {
        val clubId = request.path("id")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid club ID")
        val club = services.getClubById(Id(clubId)) ?: throw NoSuchElementException("Club not found")
        Response(Status.OK).json(club)
    }

    fun getClubs(request: Request): Response = useWithException {
        val clubs = services.getClubs()
        if (clubs.isEmpty()) throw NoSuchElementException("List is Empty")
        Response(Status.OK).json(clubs)
    }

    val appClubs = routes(
        "club" bind Method.POST to ::createClub,
        "clubs/{id}" bind Method.GET to ::getClubById,
        "clubs" bind Method.GET to ::getClubs,
    )
}

