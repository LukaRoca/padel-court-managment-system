package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.CREATED
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.slf4j.LoggerFactory
import pt.isel.ls.dto.RentalDTO
import pt.isel.ls.dto.ResponseRentalDto
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

    private fun rentalUser(request: Request): Response {
        logRequest(request)
        val rentalData = Json.decodeFromString<RentalDTO>(request.bodyString())

        val rental = rentalServices.createRental(rentalData.cid, rentalData.crid, rentalData.date, rentalData.duration)
            ?: return Response(Status.UNAUTHORIZED)
                .body(Json.encodeToString(mapOf("error" to "Invalid rental")))
        return Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(ResponseRentalDto(rental.rid)))
    }

    val appRental = routes(
        "rental" bind Method.POST to ::rentalUser,
    )
}