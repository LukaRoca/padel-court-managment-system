package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import pt.isel.ls.domain.Date
import pt.isel.ls.domain.Duration
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Token
import pt.isel.ls.isNotNegative
import pt.isel.ls.mapRentalToDetails
import pt.isel.ls.mapRentalsToDetailsList
import pt.isel.ls.validateInt
import pt.isel.ls.webApi.dto.*
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
        ) ?: throw NoSuchElementException()
        Response(CREATED).json(RentalOutput(rental.rid.id))
    }

    fun getRentalById(request: Request): Response = useWithException {
        val rentalId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid rental ID")
        val rental = rentalServices.getRentalById(Id(rentalId)) ?: throw NoSuchElementException()
        Response(OK).json(mapRentalToDetails(rental))
    }

    fun getRentalsOfUser(request: Request): Response = useWithException {
        val limit = request.query("limit")?.toInt().validateInt { it.isNotNegative() }
        val skip  = request.query("skip")?.toInt().validateInt { it.isNotNegative() }
        val userId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException()
        val rentals = rentalServices.getRentalsOfUser(Id(userId), limit, skip) ?: throw NoSuchElementException()
        Response(OK).json(rentals)
    }

    fun getRentals(request: Request): Response = useWithException {
        val cid = request.query("cid")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'cid'")
        val crid = request.query("crid")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'crid'")
        val date = request.query("date") ?: throw IllegalArgumentException("Invalid or missing 'date'")

        val rentalList = rentalServices.getRentals(Id(cid), Id(crid), Date(date)) ?: throw NoSuchElementException()
        Response(OK).json(mapRentalsToDetailsList(rentalList))
    }

    fun getRentalsOfCourt(request: Request): Response = useWithException {
        val limit = request.query("limit")?.toInt().validateInt { it.isNotNegative() }
        val skip  = request.query("skip")?.toInt().validateInt { it.isNotNegative() }
        val courtId = request.path("crid")?.toIntOrNull() ?: throw IllegalArgumentException()
        val rentals = rentalServices.getRentalsOfCourt(Id(courtId), limit, skip) ?: throw NoSuchElementException()
        Response(OK).json(rentals)
    }

    fun getAvailableHours(request: Request): Response = useWithException {
        val cid = request.query("cid")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'cid'")
        val crid = request.query("crid")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'crid'")
        val date = request.query("date") ?: throw IllegalArgumentException("Invalid or missing 'date'")

        val availableHours = rentalServices.getAvailableHours(
            Id(cid),
            Id(crid),
            Date(date)
        )
        Response(OK).json(availableHours)
    }

    fun deleteRental(request: Request): Response = useWithException {
        val rid = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid rental ID")
        val deleted = rentalServices.deleteRental(Id(rid))
        if (deleted) {
            Response(OK).json("Rental deleted sucessfuly")
        } else {
            Response(NOT_FOUND).json("Rental not found")
        }
    }

    fun updateRental(request: Request): Response = useWithException {
        val rid = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid rental ID")
        val date = request.query("date") ?: throw IllegalArgumentException("Invalid or missing 'date'")
        val initDuration = request.query("initD") ?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'initDuration'")
        val endDuration = request.query("endD") ?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'endDuration'")
        val rental = rentalServices.getRentalById(Id(rid)) ?: throw IllegalArgumentException("Invalid rental ID")
        val updatedRental = rentalServices.updateRental(Date(date),Duration(initDuration, endDuration), rental.rid)
        Response(OK).json(updatedRental)
    }
}