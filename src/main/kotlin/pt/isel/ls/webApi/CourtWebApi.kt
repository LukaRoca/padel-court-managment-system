package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.path
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Token
import pt.isel.ls.utils.isNotNegative
import pt.isel.ls.utils.validateInt
import pt.isel.ls.webApi.dto.*
import pt.isel.ls.webApi.models.club.ClubCreate
import pt.isel.ls.webServices.CourtServices

class CourtWebApi(private val courtServices: CourtServices) : WebApiExceptions() {

    fun createCourt(request: Request): Response =
        request.useWithException { token ->
            val court = Json.decodeFromString<CourtCreate>(request.bodyString())
            Response(Status.CREATED)
                .json(courtServices.createCourt(court, token))
        }

    fun getCourtById(request: Request): Response =
        request.useWithException { token ->
            val courtId = request.path("courtId")?.toInt().validateInt { it.isNotNegative() }
            Response(Status.OK)
                .json(courtServices.getCourtById(courtId, token))
        }


    fun getCourtsByClub(request: Request): Response =
        request.useWithException { token ->
            val clubId = request.path("clubId")?.toInt().validateInt { it.isPositive() }
            Response(Status.OK)
                .json(courtServices.getCourtsByClubId(clubId, token, playerId)
        }
}