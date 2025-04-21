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
import pt.isel.ls.webApi.dto.*
import pt.isel.ls.webServices.CourtServices

class CourtWebApi(private val courtServices: CourtServices) : WebApiExceptions() {

    fun createCourt(request: Request): Response = useWithException {
        val token = request.header("Authorization")?.substringAfter("Bearer ")
            ?: throw IllegalArgumentException()
        val courtDto = Json.decodeFromString<CourtInput>(request.bodyString())
        val court = courtServices.createCourt(Name(courtDto.name), Id(courtDto.cid), Token(token)) ?: throw NoSuchElementException()
        Response(CREATED).json(CourtOutput(court.id.id))
    }

    private fun getCourtById(request: Request): Response = useWithException {
        val crid = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException()
        val court = courtServices.getCourtById(Id(crid)) ?: throw NoSuchElementException()
        Response(OK).json(CourtDetails(
            court.id.id,
            court.name.name,
            ClubDetails(
                court.club.id.id,
                court.club.name.name,
                UserDetails(
                    court.club.owner.user.uid.id,
                    court.club.owner.user.name.name,
                    court.club.owner.user.email.value,
                    court.club.owner.user.token.token
                )
            )))
    }


    private fun getCourtsByClub(request: Request): Response = useWithException {
        val clubId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException()
        val courts = courtServices.getCourtsByClubId(Id(clubId)) ?: emptyList()
        if (courts.isNotEmpty()) {
            Response(OK).json(courts.map { court ->
                CourtDetails(
                    court.id.id,
                    court.name.name,
                    ClubDetails(
                        court.club.id.id,
                        court.club.name.name,
                        UserDetails(
                            court.club.owner.user.uid.id,
                            court.club.owner.user.name.name,
                            court.club.owner.user.email.value,
                            court.club.owner.user.token.token
                        )
                    ))
            })
        } else {
            throw NoSuchElementException()
        }
    }


    val appCourts = routes(
        "courts" bind Method.POST to ::createCourt,
        "courts/{id}" bind Method.GET to ::getCourtById,
        "clubs/{id}/courts" bind Method.GET to ::getCourtsByClub
    )
}