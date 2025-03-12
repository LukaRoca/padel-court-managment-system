package pt.isel.ls.webApi.routes.user

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.slf4j.LoggerFactory
import pt.isel.ls.webServices.userService


class userWebApi(private val userService: userService) {

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

    fun getUsers(request: Request): Response {
        logRequest(request)
        val users = userService.getUsers()
        return Response(OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(users))
    }
}