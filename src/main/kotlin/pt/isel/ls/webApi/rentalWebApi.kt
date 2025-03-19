package pt.isel.ls.webApi
/*
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
import pt.isel.ls.webServices.RentalServices
import pt.isel.ls.webServices.dto.RentalAvailableHoursRequestDTO
import pt.isel.ls.webServices.dto.RentalDTO
import pt.isel.ls.webServices.dto.RentalListDTO
import pt.isel.ls.webServices.dto.ResponseRentalDto
import kotlin.math.log

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

        val date = Date.fromStrings(rentalData.date, rentalData.time)

        val rental = rentalServices.createRental(rentalData.cid, rentalData.crid, date, rentalData.duration, token)
            ?: return Response(Status.NOT_FOUND)
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

    private fun getRentalList(request: Request): Response{
        logRequest(request)

        val rentalListDto = Json.decodeFromString<RentalListDTO>(request.bodyString())

        val date = Date.fromStrings(rentalListDto.date, rentalListDto.time)

        val rentalList = rentalServices.getRentalList(rentalListDto.cid,rentalListDto.crid, date)
                ?: return Response(Status.NOT_FOUND)
                    .body(Json.encodeToString(mapOf("error" to "No rentals found")))
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

        val rentals =rentalServices.getRentalsOfUser(userId)
        return Response(Status.OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rentals))
    }


    private fun getAvailableHours(request: Request): Response {
        logRequest(request)

        val dto = Json.decodeFromString<RentalAvailableHoursRequestDTO>(request.bodyString())
        val date = Date.fromStrings(dto.date, dto.time)

        val availableHours = rentalServices.getAvailableHours(dto.cid, dto.crid, date)

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