package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.path
import org.slf4j.LoggerFactory
import pt.isel.ls.domain.Email
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.Password
import pt.isel.ls.isNotNegative
import pt.isel.ls.validateInt
import pt.isel.ls.webApi.dto.UserDetails
import pt.isel.ls.webServices.UserServices
import pt.isel.ls.webApi.dto.UserOutput
import pt.isel.ls.webApi.dto.UserInput
import pt.isel.ls.webApi.dto.UserLoginInput
import pt.isel.ls.webApi.dto.UserLoginOutput

class UserWebApi(private val userServices: UserServices) : WebApiExceptions() {
    val logger = LoggerFactory.getLogger("pt.isel.ls.webApi.routes.user.UserRoute")

    fun logRequest(request: Request) {
        logger.info(
            "incoming request: method={}, uri={}, content-type={} accept={}",
            request.method,
            request.uri,
            request.header("content-type"),
            request.header("accept"),
        )
    }
    fun getUserById(request: Request): Response = useWithException {
        logRequest(request)
        val userId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid user ID")
        val user = userServices.getUserById(Id(userId)) ?: throw NoSuchElementException()
        Response(OK).json(UserDetails(user.uid.id, user.name.name, user.email.value, user.token.token))
    }


    fun createUser(request: Request): Response = useWithException {
        logRequest(request)
        val response = Json.decodeFromString<UserInput>(request.bodyString())
        try {
            val user = userServices.createUser(Name(response.name), Email(response.email), Password(response.password))
            Response(CREATED).json(UserOutput(user.uid.id, user.token.token))
        } catch (e: Exception) {
            throw e
        }
    }

    fun getAllUsers(request: Request): Response = useWithException {
        logRequest(request)
        val limit = request.query("limit")?.toInt().validateInt { it.isNotNegative() }
        val skip  = request.query("skip")?.toInt().validateInt { it.isNotNegative() }

        val paginatedResult = userServices.getAllUsers(limit, skip)

        Response(OK).json(paginatedResult)
    }
    fun loginUser(request: Request): Response = useWithException {
        logRequest(request)
        val response = Json.decodeFromString<UserLoginInput>(request.bodyString())
        try {
            val user = userServices.loginUser(Email(response.email), Password(response.password))
            Response(OK).json(UserLoginOutput(user.uid.id, user.token.token))
        } catch (e: Exception) {
            throw e
        }
    }
}