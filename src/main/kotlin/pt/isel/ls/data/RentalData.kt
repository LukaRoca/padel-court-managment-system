package pt.isel.ls.data

import pt.isel.ls.domain.*
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Id
import pt.isel.ls.webApi.models.rental.RentalCreate

interface RentalData {
    fun createRental(rentalCreate: RentalCreate, court : Id, user : Id): Rental

    fun getRentalById(rentalId: Id): Rental?

    fun getRentalsOfUser(user: Id): List<Rental>

    fun getRentals() : List<Rental>

    fun getRentalsOfCourt(court: Id): List<Rental>

    fun getAvailableHours(court: Court, date: Date): List<Int>?

    fun deleteRental(rental: Id): Boolean

    fun updateRental(date: Date, duration: Duration, rental: Id): Boolean

    fun getRentalsWithDate(date: Date): List<Rental>
}