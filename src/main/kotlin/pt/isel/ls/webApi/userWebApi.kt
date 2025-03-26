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
import org.slf4j.LoggerFactory
import pt.isel.ls.domain.Email
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.webServices.UserServices
import pt.isel.ls.webApi.dto.UserOutput
import pt.isel.ls.webApi.dto.UserInput

class UserWebApi(private val userServices: UserServices) : WebApiExceptions() {
    private fun handleError(e: Exception): Response = httpException(e)

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
    fun getUserById(request: Request): Response = try {
        logRequest(request)
        val userId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid user ID")
        val user = userServices.getUserById(Id(userId)) ?: throw NoSuchElementException()
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(user))
    } catch (e: Exception) {
        handleError(e)
    }

    fun createUser(request: Request): Response = try {
        logRequest(request)
        val response = Json.decodeFromString<UserInput>(request.bodyString())
        val user = userServices.createUser(Name(response.name), Email(response.email))
        Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(UserOutput(user.uid, user.token)))
    } catch (e: Exception) {
        handleError(e)
    }

    val app = routes(
        "users" bind Method.POST to ::createUser,
        "users/{id}" bind Method.GET to ::getUserById
    )
}