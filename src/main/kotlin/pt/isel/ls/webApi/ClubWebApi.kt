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
import pt.isel.ls.isNotNegative
import pt.isel.ls.validateInt
import pt.isel.ls.webApi.dto.ClubDetails
import pt.isel.ls.webApi.dto.ClubInput
import pt.isel.ls.webApi.dto.ClubOutput
import pt.isel.ls.webApi.dto.UserDetails
import pt.isel.ls.webServices.ClubServices


class ClubWebApi(private val clubServices: ClubServices) : WebApiExceptions() {

    fun createClub(request: Request): Response = useWithException {
        val token = request.header("Authorization")?.removePrefix("Bearer ")
            ?: throw IllegalArgumentException("Missing or invalid token")
        val clubDto = Json.decodeFromString<ClubInput>(request.bodyString())
        val club = clubServices.createClub(Name(clubDto.name), Token(token)) ?: throw NoSuchElementException()
        Response(CREATED).json(ClubOutput(club.id.id))
    }

    fun getClubById(request: Request): Response = useWithException {
        val clubId = request.path("id")?.toIntOrNull() ?: throw IllegalArgumentException()
        val club = clubServices.getClubById(Id(clubId)) ?: throw NoSuchElementException()
        Response(OK).json(ClubDetails(club.id.id, club.name.name, UserDetails(club.owner.user.uid.id,
            club.owner.user.name.name,
            club.owner.user.email.value,
            club.owner.user.token.token)))
    }

    fun getClubs(request: Request): Response = useWithException {
        val limit = request.query("limit")?.toInt().validateInt { it.isNotNegative() }
        val skip  = request.query("skip")?.toInt().validateInt { it.isNotNegative() }

        val paginatedResult = clubServices.getClubs(limit, skip)

        Response(OK).json(paginatedResult)
    }

    fun getClubByName(request: Request): Response = useWithException {
        val clubName = request.path("name")?.let { Name(it) } ?: throw IllegalArgumentException()
        val club = clubServices.getClubByName(clubName) ?: throw NoSuchElementException()
        Response(OK).json(ClubDetails(club.id.id, club.name.name, UserDetails(club.owner.user.uid.id,
            club.owner.user.name.name,
            club.owner.user.email.value,
            club.owner.user.token.token)))
    }

    fun deleteClub(request: Request): Response = useWithException {
        val clubId = request.path("id")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid club ID")

        val authToken = request.header("Authorization")?.removePrefix("Bearer ")
            ?: throw IllegalArgumentException("Missing or invalid token")

        val deleted = clubServices.deleteClub(Id(clubId), Token(authToken))
        if (deleted) {
            Response(OK).json("Club with ID $clubId deleted successfully")
        } else {
            Response(OK).json("Failed to delete club with ID $clubId")
        }
    }

    }





