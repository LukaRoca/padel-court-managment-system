package pt.isel.ls.webApi
import kotlinx.serialization.json.Json
import org.http4k.core.Response
import org.http4k.core.Status

abstract class WebApiExceptions {
    inline fun <reified T> Response.json(body: T): Response {
        return this
            .header("content-type", "application/json")
            .body(Json.encodeToString(body))
    }

    inline fun useWithException(block: () -> Response): Response {
        return try {
            block()
        } catch (e: Exception) {
            httpException(e)
        }
    }

    fun httpException(e: Exception): Response {
        return when (e) {
            is NoSuchElementException -> Response(Status.NOT_FOUND).json(e.message ?: "Not found")
            is IllegalArgumentException -> Response(Status.BAD_REQUEST).json("Illegal argument: ${e.message}")
            is AuthorizationException -> Response(Status.UNAUTHORIZED).json(e.message ?: "Unauthorized")
            else -> Response(Status.INTERNAL_SERVER_ERROR).json(e.message ?: "Internal server error")
        }
    }
}