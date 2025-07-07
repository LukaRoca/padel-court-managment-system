package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.path
import pt.isel.ls.utils.isNotNegative
import pt.isel.ls.utils.validateInt
import pt.isel.ls.webApi.models.club.ClubCreate
import pt.isel.ls.webServices.ClubServices


class ClubWebApi(private val clubServices: ClubServices) : APISchema() {

    fun createClub(request: Request): Response =
        request.useWithException { token ->
            val gameInput = Json.decodeFromString<ClubCreate>(request.bodyString())
            Response(Status.CREATED)
                .json(clubServices.createGame(gameInput, token))
        }

    fun getClubById(request: Request): Response =
        request.useWithException { token ->
            val clubId = request.path("clubId")?.toInt().validateInt { it.isNotNegative() }
            Response(Status.OK)
                .json(clubServices.getClubById(clubId, token))
        }

    fun getClubs(request: Request): Response =
        request.useWithException { token ->
            Response(OK).json(clubServices.getClubs(token))
        }

    fun getClubByName(request: Request): Response =
        request.useWithException { token ->
            val clubName = request.path("clubName").toString()
            Response(Status.OK)
                .json(clubServices.getClubById(clubName, token))
        }

    }





