package pt.isel.ls.storage.iStorage

import pt.isel.ls.domain.*

interface RentalIStorage {
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