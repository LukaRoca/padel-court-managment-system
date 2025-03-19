package pt.isel.ls.storage

import pt.isel.ls.domain.*

interface RentalIStorage {

    fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token): Rental?

    fun getRentalById(rentalId: Id): Rental?

    fun getRentalList(cid: Id, crid: Id, date: Date): List<Rental>?

    fun getRentalsOfUser(cid: Id): List<Rental>?

    fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int>

}