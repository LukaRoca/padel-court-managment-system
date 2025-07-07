package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.eclipse.jetty.server.Session.API
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.path
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Token
import pt.isel.ls.utils.isNotNegative
import pt.isel.ls.utils.validateInt
import pt.isel.ls.webApi.models.club.ClubCreate
import pt.isel.ls.webApi.models.court.CourtCreate
import pt.isel.ls.webServices.CourtServices

class CourtWebApi(private val courtServices: CourtServices) : APISchema() {

    fun createCourt(request: Request): Response =
        request.useWithException { token ->
            val court = Json.decodeFromString<CourtCreate>(request.bodyString())
            val cid = request.query("cid")?.toInt().validateInt { it.isNotNegative() }
            Response(Status.CREATED)
                .json(courtServices.createCourt(court, cid, token))
        }

    fun getCourtById(request: Request): Response =
        request.useWithException { token ->
            val courtId = request.path("id")?.toInt().validateInt { it.isNotNegative() }
            Response(Status.OK)
                .json(courtServices.getCourtById(Id(courtId), token))
        }


    fun getCourtsByClub(request: Request): Response =
        request.useWithException { token ->
            val clubId = request.path("id")?.toInt().validateInt { it.isNotNegative() }
            Response(Status.OK)
                .json(courtServices.getCourtsByClubId(Id(clubId), token, DEFAULT_LIMIT, DEFAULT_SKIP))
        }
}