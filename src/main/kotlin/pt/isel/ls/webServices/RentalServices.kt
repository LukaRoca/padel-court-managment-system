package pt.isel.ls.webServices

import pt.isel.ls.checkIfTokenInDb
import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.RentalIStorage
import pt.isel.ls.storage.iStorage.UserIStorage
import pt.isel.ls.webApi.TokenNotFoundException

class RentalServices (private val db : RentalIStorage, private val userDb: UserIStorage) {

    fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token ): Rental? {

        return if (checkIfTokenInDb(token, userDb)) {
            db.createRental(cid, crid, date, duration, token)
        }else{
            throw TokenNotFoundException("No user found with that token")
        }
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

