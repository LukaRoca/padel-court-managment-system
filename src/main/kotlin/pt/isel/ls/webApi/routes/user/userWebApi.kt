package pt.isel.ls.webApi.routes.user

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
import pt.isel.ls.webApi.user.UserDTO

class UserWebApi(private val userService: UserServices) {

    private val logger = LoggerFactory.getLogger("pt.isel.ls.webApi.routes.user.UserRoute")

    fun logRequest(request: Request) {
        logger.info(
            "incoming request: method={}, uri={}, content-type={} accept={}",
            request.method,
            request.uri,
            request.header("content-type"),
            request.header("accept"),
        )
    }
    fun getUserById(request: Request): Response {
        logRequest(request)

        val userId = request.path("id")?.toIntOrNull()
        if (userId == null) {
            return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid user ID")))
        }

        val user = userService.getUserById(userId)
        return if (user != null) {
            Response(OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(user))
        } else {
            Response(Status.NOT_FOUND)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "User not found")))
        }
    }
    /*
    fun getUsers(request: Request): Response {
        logRequest(request)
        val users = userService.getUsers()
        return Response(OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(users))
    }}
    */
    fun createUser(request: Request): Response {
        logRequest(request)
        val user = Json.decodeFromString<UserDTO>(request.bodyString())
        val createduser = userService.createUser(user.name, user.email)
        return Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(createduser))
    }
    val app = routes(
        "users" bind Method.POST to ::createUser
    )
}