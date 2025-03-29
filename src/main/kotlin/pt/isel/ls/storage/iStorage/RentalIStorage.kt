package pt.isel.ls.storage.iStorage

import pt.isel.ls.domain.*

interface RentalIStorage {

    fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token): Rental?
    fun getRentalById(rentalId: Id): Rental?
    fun getRentalList(cid: Id, crid: Id, date: Date): List<Rental>?
    fun getRentalsOfUser(uid: Id): List<Rental>?
    fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int>

}