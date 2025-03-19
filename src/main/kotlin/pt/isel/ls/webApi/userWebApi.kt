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
import pt.isel.ls.domain.Email
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.webServices.UserServices
import pt.isel.ls.webApi.dto.UserOutput
import pt.isel.ls.webApi.dto.UserInput

class UserWebApi(private val services: UserServices) : WebApiExceptions() {

    fun createUser(request: Request): Response = useWithException {
        val userInput = Json.decodeFromString<UserInput>(request.bodyString())
        val user = services.createUser(Name(userInput.name), Email(userInput.email))
        Response(CREATED).json(UserOutput(user.uid, user.token))
    }

    fun getUserById(request: Request): Response = useWithException {
        val userId = request.path("id")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid user ID")
        val user = services.getUserById(Id(userId)) ?: throw NoSuchElementException("User not found")
        Response(OK).json(user)
    }

    val app = routes(
        "users" bind Method.POST to ::createUser,
        "users/{id}" bind Method.GET to ::getUserById
    )
}