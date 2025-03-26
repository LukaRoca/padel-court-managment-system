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
import pt.isel.ls.domain.Date
import pt.isel.ls.domain.Duration
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Token
import pt.isel.ls.webApi.dto.RentalAvailableHoursRequestDTO
import pt.isel.ls.webApi.dto.RentalInput
import pt.isel.ls.webApi.dto.RentalOutput
import pt.isel.ls.webServices.RentalServices

class RentalWebApi(private val rentalServices: RentalServices) : WebApiExceptions() {
    private fun handleError(e: Exception): Response = httpException(e)
    fun createRental(request: Request): Response = try {
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
    } catch (e: Exception) {
        handleError(e)
    }

    fun getRentalById(request: Request): Response = try {
        val rentalId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid rental ID")
        val rental = rentalServices.getRentalById(Id(rentalId)) ?: throw NoSuchElementException("Rental not found")
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rental))
    } catch (e: Exception) {
        handleError(e)
    }

    fun getRentalList(request: Request): Response = try {
        val rentalListDto = Json.decodeFromString<RentalInput>(request.bodyString())
        val rentalList = rentalServices.getRentalList(
            Id(rentalListDto.cid),
            Id(rentalListDto.crid),
            Date(rentalListDto.date)
        )
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rentalList))
    } catch (e: Exception) {
        handleError(e)
    }

    fun getRentalsOfUser(request: Request): Response = try {
        val userId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid user ID")
        val rentals = rentalServices.getRentalsOfUser(Id(userId))
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rentals))
    } catch (e: Exception) {
        handleError(e)
    }

    fun getAvailableHours(request: Request): Response = try {
        val availableHoursRequest = Json.decodeFromString<RentalAvailableHoursRequestDTO>(request.bodyString())
        val availableHours = rentalServices.getAvailableHours(
            availableHoursRequest.cid,
            availableHoursRequest.crid,
            availableHoursRequest.date
        )
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(availableHours))
    } catch (e: Exception) {
        handleError(e)
    }

    val appRental = routes(
        "rental" bind Method.POST to ::createRental,
        "/rental/{id}" bind Method.GET to ::getRentalById,
        "/rentals" bind Method.GET to ::getRentalList,
        "/rentals/user/{id}" bind Method.GET to ::getRentalsOfUser,
        "/rentals/available" bind Method.GET to ::getAvailableHours
    )
}
