package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.path
import pt.isel.ls.DEFAULT_LIMIT
import pt.isel.ls.DEFAULT_SKIP
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.isNotNegative
import pt.isel.ls.utils.validateInt
import pt.isel.ls.webApi.models.club.ClubCreate
import pt.isel.ls.webServices.ClubServices


class ClubWebApi(private val clubServices: ClubServices) : APISchema() {
    fun createClub(request: Request): Response =
        request.useWithException { token ->
            val clubInput = Json.decodeFromString<ClubCreate>(request.bodyString())
            Response(Status.CREATED)
                .json(clubServices.createClub(clubInput, token))
        }

    fun getClubById(request: Request): Response =
        request.useWithException { token ->
            val clubId = request.path("clubId")?.toInt().validateInt { it.isNotNegative() }
            Response(Status.OK)
                .json(clubServices.getClubById(Id(clubId), token))
        }

    fun getClubs(request: Request): Response =
        request.useWithException { token ->
            val skip = request.query("skip")?.toInt().validateInt(DEFAULT_SKIP) { it.isNotNegative() }
            val limit = request.query("limit")?.toInt().validateInt(DEFAULT_LIMIT) { it.isNotNegative() }
            Response(OK).json(clubServices.getClubs(token, skip, limit))
        }

    fun getClubByName(request: Request): Response =
        request.useWithException { token ->
            val clubName = request.path("clubName").toString()
            Response(Status.OK)
                .json(clubServices.getClubByName(clubName, token))
        }

    }





