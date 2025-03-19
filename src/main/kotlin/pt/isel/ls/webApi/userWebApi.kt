package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.slf4j.LoggerFactory
import pt.isel.ls.domain.Email
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.webServices.UserServices
import pt.isel.ls.webApi.dto.UserOutput
import pt.isel.ls.webApi.dto.UserInput

class UserWebApi(private val userServices: UserServices) {

    private val logger = LoggerFactory.getLogger("pt.isel.ls.webApi.routes.user.UserRoute")

    private fun logRequest(request: Request) {
        logger.info(
            "incoming request: method={}, uri={}, content-type={} accept={}",
            request.method,
            request.uri,
            request.header("content-type"),
            request.header("accept"),
        )
    }

    private fun getUserById(request: Request): Response {
        logRequest(request)
        val userId = request.path("id")?.toIntOrNull() ?: return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid user ID")))

        val user = UserServices.getUserById(Id(userId))

        return when (user) {
            null -> Response(Status.NOT_FOUND, "User not found")
            else -> Response(OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(UserOutput(user.uid, user.token)))
        }

    }

    fun createUser(request: Request): Response {
        logRequest(request)
        val response = Json.decodeFromString<UserInput>(request.bodyString())

        val user = UserServices.createUser(Name(response.name), Email(response.email))

        return Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(UserOutput(user.uid, user.token)))
    }

    //Rotas
    val app = routes(
        "users" bind Method.POST to ::createUser,
        "users/{id}" bind Method.GET to ::getUserById
    )
}