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
        ) ?: throw NoSuchElementException()
        Response(CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(RentalOutput(rental.rid)))
    } catch (e: Exception) {
        handleError(e)
    }

    fun getRentalById(request: Request): Response = try {
        val rentalId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid rental ID")
        val rental = rentalServices.getRentalById(Id(rentalId)) ?: throw NoSuchElementException()
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rental))
    } catch (e: Exception) {
        handleError(e)
    }

    fun getRentalList(request: Request): Response = try {
        val cid = request.query("cid")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'cid'")
        val crid = request.query("crid")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'crid'")
        val date = request.query("date") ?: throw IllegalArgumentException("Invalid or missing 'date'")

        val rentalList = rentalServices.getRentalList(
            Id(cid),
            Id(crid),
            Date(date)
        )
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rentalList))
    } catch (e: Exception) {
        handleError(e)
    }

    fun getRentalsOfUser(request: Request): Response = try {
        val userId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException()
        val rentals = rentalServices.getRentalsOfUser(Id(userId))
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(rentals))
    } catch (e: Exception) {
        handleError(e)
    }

    fun getRentalsOfCourt(request: Request): Response = try {
        val courtId = request.path("crid")?.toIntOrNull() ?: throw IllegalArgumentException()
        val rentals = rentalServices.getRentalsOfCourt(Id(courtId))
        Response(OK)
        .header("content-type", "application/json")
        .body(Json.encodeToString(rentals))
    } catch (e: Exception) {
        handleError(e)
    }

    fun getAvailableHours(request: Request): Response = try {
        val cid = request.query("cid")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'cid'")
        val crid = request.query("crid")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'crid'")
        val date = request.query("date") ?: throw IllegalArgumentException("Invalid or missing 'date'")
        val initDuration = request.query("initDuration")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'initDuration'")
        val endDuration = request.query("endDuration")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'endDuration'")

        val availableHours = rentalServices.getAvailableHours(
            Id(cid),
            Id(crid),
            Date(date),
            Duration(initDuration, endDuration)
        )
        Response(OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(availableHours))
    } catch (e: Exception) {
        handleError(e)
    }

    val appRental = routes(
        "rental" bind Method.POST to ::createRental,
        "rentals/{id}" bind Method.GET to ::getRentalById,
        "rentals" bind Method.GET to ::getRentalList,
        "rentals/user/{id}" bind Method.GET to ::getRentalsOfUser,
        "rentals/available" bind Method.GET to ::getAvailableHours,
        "rentals/courts/{crid}" bind Method.GET to ::getRentalsOfCourt
    )
}
