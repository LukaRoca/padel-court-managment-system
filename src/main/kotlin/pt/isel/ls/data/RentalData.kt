package pt.isel.ls.data

import pt.isel.ls.domain.*
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Id

interface RentalData {
    fun createRental(court: Court, date: Date, duration: Duration, user: User): Rental?

    fun getRentalById(rentalId: Id): Rental?

    fun getRentalsOfUser(user: User): List<Rental>?

    fun getRentals(club: Club, court: Court, date: Date) : List<Rental>?

    fun getRentalsOfCourt(court: Court): List<Rental>?

    fun getAvailableHours(club: Club, court: Court, date: Date): List<Int>?

    fun deleteRental(rental: Rental): Boolean

    fun updateRental(date: Date, duration: Duration, rental: Rental): Rental?

    fun getRentalsWithDate(date: Date): List<Rental>
}