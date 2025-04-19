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
import pt.isel.ls.webApi.dto.*
import pt.isel.ls.webServices.RentalServices
import kotlin.math.absoluteValue

class RentalWebApi(private val rentalServices: RentalServices) : WebApiExceptions() {
    private fun handleError(e: Exception): Response = httpException(e)

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

    private fun getRentalById(request: Request): Response = useWithException {
        val rentalId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid rental ID")
        val rental = rentalServices.getRentalById(Id(rentalId)) ?: throw NoSuchElementException()
        Response(OK).json(RentalDetails(
            rental.rid.id,
            rental.date.value,
            rental.duration.hours,
            UserDetails(
                rental.user.uid.id,
                rental.user.name.name,
                rental.user.email.value,
                rental.user.token.token
            ),
            CourtDetails(
                rental.court.id.id,
                rental.court.name.name,
                ClubDetails(
                    rental.court.club.id.id,
                    rental.court.club.name.name,
                    UserDetails(
                        rental.user.uid.id,
                        rental.user.name.name,
                        rental.user.email.value,
                        rental.user.token.token
                    )
                )
            )
        ))

    }

    private fun getRentalsOfUser(request: Request): Response = useWithException {
        val userId = request.path("id")?.toIntOrNull()
            ?: throw IllegalArgumentException()
        val rentals = rentalServices.getRentalsOfUser(Id(userId)) ?: throw NoSuchElementException()
        Response(OK).json(rentals.map { rental ->
            RentalDetails(
                rental.rid.id,
                rental.date.value,
                rental.duration.hours,
                UserDetails(
                    rental.user.uid.id,
                    rental.user.name.name,
                    rental.user.email.value,
                    rental.user.token.token
                ),
                CourtDetails(
                    rental.court.id.id,
                    rental.court.name.name,
                    ClubDetails(
                        rental.court.club.id.id,
                        rental.court.club.name.name,
                        UserDetails(
                            rental.user.uid.id,
                            rental.user.name.name,
                            rental.user.email.value,
                            rental.user.token.token
                        )
                    )
                )
            )
        })
    }

    private fun getRentals(request: Request) : Response = useWithException {
        val cid = request.query("cid")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'cid'")
        val crid = request.query("crid")?.toIntOrNull() ?: throw IllegalArgumentException("Invalid or missing 'crid'")
        val date = request.query("date") ?: throw IllegalArgumentException("Invalid or missing 'date'")

        val rentalList = rentalServices.getRentals(Id(cid), Id(crid), Date(date)) ?: throw NoSuchElementException()
        Response(OK).json(rentalList.map { rental ->
            RentalDetails(
                rental.rid.id,
                rental.date.value,
                rental.duration.hours,
                UserDetails(
                    rental.user.uid.id,
                    rental.user.name.name,
                    rental.user.email.value,
                    rental.user.token.token
                ),
                CourtDetails(
                    rental.court.id.id,
                    rental.court.name.name,
                    ClubDetails(
                        rental.court.club.id.id,
                        rental.court.club.name.name,
                        UserDetails(
                            rental.user.uid.id,
                            rental.user.name.name,
                            rental.user.email.value,
                            rental.user.token.token
                        )
                    )
                )
            )
        })
    }

    private fun getRentalsOfCourt(request: Request): Response = useWithException {
        val courtId = request.path("crid")?.toIntOrNull() ?: throw IllegalArgumentException()
        val rentals = rentalServices.getRentalsOfCourt(Id(courtId)) ?: throw NoSuchElementException()
        Response(OK).json(rentals.map{ rental ->
            RentalDetails(
                rental.rid.id,
                rental.date.value,
                rental.duration.hours,
                UserDetails(
                    rental.user.uid.id,
                    rental.user.name.name,
                    rental.user.email.value,
                    rental.user.token.token
                ),
                CourtDetails(
                    rental.court.id.id,
                    rental.court.name.name,
                    ClubDetails(
                        rental.court.club.id.id,
                        rental.court.club.name.name,
                        UserDetails(
                            rental.user.uid.id,
                            rental.user.name.name,
                            rental.user.email.value,
                            rental.user.token.token
                        )
                    )
                )
            )
        })
    }

    private fun getAvailableHours(request: Request): Response = useWithException {
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

    val appRental = routes(
        "rental" bind Method.POST to ::createRental,
        "rentals/available" bind Method.GET to ::getAvailableHours,
        "rentals/{id}" bind Method.GET to ::getRentalById,
        "rentals/user/{id}" bind Method.GET to ::getRentalsOfUser,
        "rentals" bind Method.GET to ::getRentals,
        "rentals/courts/{crid}" bind Method.GET to ::getRentalsOfCourt
    )
}
