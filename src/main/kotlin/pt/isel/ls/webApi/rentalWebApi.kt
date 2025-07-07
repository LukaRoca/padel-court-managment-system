package pt.isel.ls.webApi

import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.path
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.isNotNegative
import pt.isel.ls.utils.mapRentalToDetails
import pt.isel.ls.utils.mapRentalsToDetailsList
import pt.isel.ls.utils.validateInt
import pt.isel.ls.webApi.models.rental.RentalCreate
import pt.isel.ls.webApi.models.rental.RentalDetails
import pt.isel.ls.webServices.RentalServices

class RentalWebApi(private val rentalServices: RentalServices) : APISchema() {
    fun createRental(request: Request): Response = request.useWithException { token ->
        val rentalData = Json.decodeFromString<RentalCreate>(request.bodyString())
        Response(Status.CREATED)
            .json(rentalServices.createRental(rentalData, rentalData.courtId, token))
    }

    fun getRentalById(request: Request): Response = request.useWithException { token ->
        val rentalId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid rental ID")
        val rental = rentalServices.getRentalById(Id(rentalId), token) ?: throw NoSuchElementException()
        Response(OK).json<RentalDetails>(
            mapRentalToDetails(
                rental,
                getClubById = { clubId -> rentalServices.getClubById(clubId, token)!! },
                getCourtById = { courtId -> rentalServices.getCourtById(courtId, token)!! },
                getUserById = { userId -> rentalServices.getUserById(userId, token)!! }
            )
        )    }

    fun getRentalsOfUser(request: Request): Response = request.useWithException { token ->
        val limit = request.query("limit")?.toInt().validateInt { it.isNotNegative() }
        val skip = request.query("skip")?.toInt().validateInt { it.isNotNegative() } ?: 0
        val userId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException()
        val rentals = rentalServices.getRentalsOfUser(Id(userId),token, limit, skip) ?: throw NoSuchElementException()
        Response(OK).json(rentals)
    }

    fun getRentalsOfCourt(request: Request): Response = request.useWithException { token ->
        val limit = request.query("limit")?.toInt().validateInt { it.isNotNegative() }
        val skip = request.query("skip")?.toInt().validateInt { it.isNotNegative() } ?: 0
        val courtId = request.path("crid")?.toIntOrNull() ?: throw IllegalArgumentException()
        val rentals = rentalServices.getRentalsOfCourt(Id(courtId),token, limit, skip) ?: throw NoSuchElementException()
        Response(OK).json(rentals)
    }

    fun getAvailableHours(request: Request): Response = request.useWithException { token ->
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

    fun deleteRental(request: Request): Response = request.useWithException { token ->
        val rentalId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid rental ID")
        val deleted = rentalServices.deleteRental(Id(rentalId), token)
        if (deleted) {
            Response(OK).json("Rental deleted sucessfuly")
        } else {
            Response(NOT_FOUND).json("Rental not found")
        }
    }

    fun updateRental(request: Request): Response = request.useWithException { token ->
        val rentalId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid rental ID")
        val date = request.query("date") ?: throw IllegalArgumentException("Invalid or missing 'date'")
        val initDuration = request.query("initD")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'initDuration'")
        val endDuration = request.query("endD")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'endDuration'")
        val rental = rentalServices.getRentalById(Id(rentalId), token) ?: throw IllegalArgumentException("Invalid rental ID")
        val updatedRental = rentalServices.updateRental(Date(date), Duration(initDuration, endDuration), Id(rentalId))
            ?: throw NoSuchElementException("Rental not found or update failed")
        Response(OK).json(updatedRental)
    }

    fun getRentalsWithDate(request: Request): Response = request.useWithException { token ->
        val date = request.query("date") ?: throw IllegalArgumentException("Invalid or missing 'date'")
        val rentals = rentalServices.getRentalsWithDate(Date(date), token) ?: throw NoSuchElementException("No rentals found for the given date")
        Response(OK).json(mapRentalsToDetailsList(rentals, courts = listOf(), clubs = listOf(), users = listOf()))
    }
}