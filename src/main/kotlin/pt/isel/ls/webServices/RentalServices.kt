package pt.isel.ls.webServices

import pt.isel.ls.checkIfTokenInDb
import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.RentalIStorage
import pt.isel.ls.storage.iStorage.UserIStorage

class RentalServices (private val db : RentalIStorage, private val userDb: UserIStorage) {

    fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token): Rental? {
        checkIfTokenInDb(token,userDb)
        val existingRentals = db.getRentalList(cid, crid, date) ?: emptyList()
        for (rental in existingRentals) {
            val existingStart = rental.duration.initDuration
            val existingEnd = rental.duration.endDuration
            val newStart = duration.initDuration
            val newEnd = duration.endDuration
            if (newStart < existingEnd && newEnd > existingStart) {
                throw IllegalArgumentException("The selected time slot is already occupied")
            }
        }
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

    fun getRentalsOfCourt(crid: Id) : List<Rental>? {
        return db.getRentalsOfCourt(crid)
    }

    fun getAvailableHours(cid: Id, crid: Id, date: Date, duration: Duration): List<Int> {
        return db.getAvailableHours(cid, crid, date, duration)
    }
}

