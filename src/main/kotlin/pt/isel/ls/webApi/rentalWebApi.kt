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
import pt.isel.ls.domain.Date
import pt.isel.ls.domain.Duration
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Token
import pt.isel.ls.webApi.dto.RentalAvailableHoursRequestDTO
import pt.isel.ls.webApi.dto.RentalInput
import pt.isel.ls.webApi.dto.RentalOutput
import pt.isel.ls.webServices.RentalServices


class RentalWebApi(private val rentalServices: RentalServices) : WebApiExceptions() {

    fun createRental(request: Request): Response = useWithException {
        val token = request.header("Authorization")?.removePrefix("Bearer ")
            ?: throw AuthorizationException("Missing or invalid token")
        val rentalData = Json.decodeFromString<RentalInput>(request.bodyString())
        val rental = rentalServices.createRental(
            Id(rentalData.cid),
            Id(rentalData.crid),
            Date(rentalData.date),
            Duration(rentalData.initDuration, rentalData.endDuration),
            Token(token)
        ) ?: throw NoSuchElementException("Invalid rental")
        Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(RentalOutput(rental.rid)))
    }

    fun getRentalById(request: Request): Response = useWithException {
        val rentalId = request.path("id")?.toIntOrNull()
            ?: return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid rental ID")))

        return try {
            val rental = rentalServices.getRentalById(Id(rentalId))
            if (rental != null) {
                Response(Status.OK)
                    .header("content-type", "application/json")
                    .body(Json.encodeToString(rental))
            } else {
                Response(Status.NOT_FOUND)
                    .header("content-type", "application/json")
                    .body(Json.encodeToString(mapOf("error" to "Rental not found")))
            }
        } catch (e: Exception) {
            Response(Status.INTERNAL_SERVER_ERROR)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to e.message)))
        }
    }

   private fun getRentalList(request: Request): Response = useWithException {
        val rentalListDto = Json.decodeFromString<RentalInput>(request.bodyString())
        val rentalList = rentalServices.getRentalList(
            Id(rentalListDto.cid),
            Id(rentalListDto.crid),
            Date(rentalListDto.date)
        )
        Response(Status.OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rentalList))
    }

    private fun getRentalsOfUser(request: Request): Response = useWithException {
        val userId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid user ID")
        val rentals = rentalServices.getRentalsOfUser(Id(userId))
        Response(Status.OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rentals))
    }

    private fun getAvailableHours(request: Request): Response = useWithException {
        val availableHoursRequest = Json.decodeFromString<RentalAvailableHoursRequestDTO>(request.bodyString())
        val availableHours = rentalServices.getAvailableHours(
            availableHoursRequest.cid,
            availableHoursRequest.crid,
            availableHoursRequest.date
        )
        Response(Status.OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(availableHours))
    }

    val appRental = routes(
        "rental" bind Method.POST to ::createRental,
        "/rental/{id}" bind Method.GET to ::getRentalById,
        "/rentals" bind Method.GET to ::getRentalList,
        "/rentals/user/{id}" bind Method.GET to ::getRentalsOfUser,
        "/rentals/available" bind Method.GET to ::getAvailableHours
    )
}

/*
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
import pt.isel.ls.domain.Duration
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Token  
import pt.isel.ls.webApi.dto.RentalAvailableHoursRequestDTO
import pt.isel.ls.webApi.dto.RentalInput
import pt.isel.ls.webApi.dto.RentalOutput
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

        val rentalData = Json.decodeFromString<RentalInput>(request.bodyString())

        val date = rentalData.date

        val rental = rentalServices.createRental(Id(rentalData.cid), Id(rentalData.crid), Date(date), Duration(rentalData.initDuration, rentalData.endDuration), Token(token))
            ?: return Response(Status.NOT_FOUND)
                .body(Json.encodeToString(mapOf("error" to "Invalid rental")))
        return Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(RentalOutput(rental.rid))
            )
    }

    private fun getRentalById(request: Request): Response {
        logRequest(request)

        val rentalId = request.path("id")?.toIntOrNull()
            ?: return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid rental ID")))

        val rental = rentalServices.getRentalById(Id(rentalId))
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

    private fun getRentalList(request: Request): Response{
        logRequest(request)

        val rentalListDto = Json.decodeFromString<RentalInput>(request.bodyString())

        val date = rentalListDto.date

        val rentalList = rentalServices.getRentalList(Id(rentalListDto.cid),Id(rentalListDto.crid), Date(date))

        return Response(Status.OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rentalList))

    }

    private fun getRentalsOfUser(request: Request): Response{
        logRequest(request)

        val userId  = request.path("id")?.toIntOrNull()
            ?: return Response(Status.BAD_REQUEST)
                .header("content-type", "application/json")
                .body(Json.encodeToString(mapOf("error" to "Invalid user ID")))

        val rentals =rentalServices.getRentalsOfUser(Id(userId))
        return Response(Status.OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rentals))
    }


    private fun getAvailableHours(request: Request): Response {
        logRequest(request)

        val availableHoursRequest = Json.decodeFromString<RentalAvailableHoursRequestDTO>(request.bodyString())

        val date = availableHoursRequest.date

        val availableHours = rentalServices.getAvailableHours(
            availableHoursRequest.cid,
            availableHoursRequest.crid,
            date
        )

        return Response(Status.OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(availableHours))
    }

    val appRental = routes(
        "rental" bind Method.POST to ::createRental,
        "/rental/{id}" bind Method.GET to ::getRentalById,
        "/rentals" bind Method.GET to ::getRentalList,
        "/rentals/user/{id}" bind Method.GET to ::getRentalsOfUser,
        "/rentals/available" bind Method.GET to ::getAvailableHours
    )
}
 */