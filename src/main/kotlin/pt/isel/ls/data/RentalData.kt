package pt.isel.ls.data

import pt.isel.ls.domain.*
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.PaginatedResponse
import pt.isel.ls.webApi.models.rental.RentalCreate
import pt.isel.ls.webApi.models.rental.RentalResponse

interface RentalData {
    fun createRental(rentalCreate: RentalCreate, court : Id, user : Id): Rental

    fun getRentalById(rentalId: Id): Rental?

    fun getRentalsOfUser(user: Id, limit: Int, skip: Int): PaginatedResponse<RentalResponse>

    fun getRentalsOfCourt(court: Id, limit: Int, skip: Int): PaginatedResponse<RentalResponse>

    fun getRentalsWithDate(date: Date): List<Rental>

    fun getAvailableHours(court: Court, date: Date): List<Int>?

    fun deleteRental(rental: Id): Boolean

    fun updateRental(date: Date, duration: Duration, rental: Id): Boolean

}