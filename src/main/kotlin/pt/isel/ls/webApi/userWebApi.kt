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
import pt.isel.ls.webApi.models.user.UserCreate
import pt.isel.ls.webApi.models.user.UserLogin
import pt.isel.ls.webApi.models.user.UserSearch
import pt.isel.ls.webServices.UserServices

class userWebApi(val services: UserServices) : APISchema() {
    fun createUser(request: Request): Response =
        request.useWithExceptionNoToken {
            val input = Json.decodeFromString<UserCreate>(request.bodyString())
            Response(Status.CREATED)
                .json(services.createUser(input))
        }

    fun getUser(request: Request): Response =
        request.useWithException { token ->
            val userId = request.path("id")?.toInt().validateInt { it.isNotNegative() }
            Response(Status.OK)
                .json(
                    services.getUser(Id(userId), token),
                )
        }

    fun getAllUsers(request: Request): Response =
        request.useWithException { token ->
            val value = Json.decodeFromString<UserSearch>(request.bodyString())
            val skip = request.query("skip")?.toInt().validateInt(DEFAULT_SKIP) { it.isNotNegative() }
            val limit = request.query("limit")?.toInt().validateInt(DEFAULT_LIMIT) { it.isNotNegative() }
            Response(OK).json(services.getAllUsers(value, token,skip,limit))
        }

    fun loginUser(request: Request): Response =
        request.useWithExceptionNoToken {
            val input = Json.decodeFromString<UserLogin>(request.bodyString())
            Response(Status.OK)
                .json(services.loginUser(input))
        }
}
