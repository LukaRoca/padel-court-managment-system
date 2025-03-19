package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.CREATED
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.slf4j.LoggerFactory
import pt.isel.ls.domain.Date
import pt.isel.ls.webServices.dto.RentalDTO
import pt.isel.ls.webServices.dto.ResponseRentalDto
import pt.isel.ls.webServices.RentalServices

class RentalWebApi(private val rentalServices: RentalServices) {

    private val logger = LoggerFactory.getLogger("pt.isel.ls.webApi.routes.user.RentalRoute")

    private fun logRequest(request: Request) {
        logger.info(
            "incoming request: method={}, uri={}, content-type={} accept={}",
            request.method,
            request.uri,
            request.header("content-type"),
            request.header("accept"),
        )
    }

    private fun createRental(request: Request): Response {
        logRequest(request)
        val token = request.header("Authorization")?.removePrefix("Bearer ")
            ?: return Response(Status.UNAUTHORIZED)
                .body(Json.encodeToString(mapOf("error" to "Missing or invalid token")))

        val rentalData = Json.decodeFromString<RentalDTO>(request.bodyString())

        val rental = rentalServices.createRental(rentalData.cid, rentalData.crid, Date(rentalData.date), rentalData.duration, token)
            ?: return Response(Status.UNAUTHORIZED)
                .body(Json.encodeToString(mapOf("error" to "Invalid rental")))
        return Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(ResponseRentalDto(rental.rid.id))
            )
    }

    private fun getRentalById(request: Request): Response {
        logRequest(request)

        val rentalId = request.path("id")?.toIntOrNull()
            ?: return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid rental ID")))

        val rental = rentalServices.getRentalById(rentalId)
        return if (rental != null) {
            Response(Status.OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(rental))
        } else {
            Response(Status.NOT_FOUND)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Rental not found")))
        }
    }

    val appRental = routes(
        "rentals" bind Method.POST to ::createRental,
        "/rental/{id}" bind Method.GET to ::getRentalById
    )
}