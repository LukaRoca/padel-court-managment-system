package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.RentalDataMem
import pt.isel.ls.storage.RentalIStorage
import pt.isel.ls.storage.UserIStorage

class RentalServices (private val db : RentalIStorage) {

    fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token ): Rental? {
        return db.createRental(cid, crid, date, duration, token)
    }

    fun getRentalById(rentalId: Id): Rental? {
        return db.getRentalById(rentalId)

    }

    fun getRentalList(cid: Id, crid: Id, date: Date): List<Rental>? {
        return db.getRentalList(cid, crid, date)
    }

    fun getRentalsOfUser(cid: Id): List<Rental>? {
        return db.getRentalsOfUser(cid)
    }

    fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int> {
        return db.getAvailableHours(cid, crid, date)
    }

}

