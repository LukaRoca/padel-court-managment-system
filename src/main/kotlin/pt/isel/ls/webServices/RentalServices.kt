package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.IStorage
import java.lang.IllegalStateException

class RentalServices (private val db : IStorage) {

    fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token ): Rental? {
        val court = db.court.getCourtById(crid) ?: throw IllegalStateException("Court not found with this id $crid")
        val user = db.user.getUserByToken(token) ?: throw IllegalStateException("User not found with this token")
        val club = db.club.getClubById(cid) ?: throw IllegalStateException("Club not found with this id $cid")
        val existingRentals = db.rental.getRentals(club,court,date) ?: emptyList()
        for (rental in existingRentals) {
            val existingStart = rental.duration.initDuration
            val existingEnd = rental.duration.endDuration
            val newStart = duration.initDuration
            val newEnd = duration.endDuration
            if (newStart < existingEnd && newEnd > existingStart) {
                throw IllegalArgumentException("The selected time slot is already occupied")
            }
        }
        return db.rental.createRental(court, date, duration, user)
    }

    fun getRentalById(rentalId: Id): Rental? {
        return db.rental.getRentalById(rentalId)

    }

    fun getRentalsOfUser(uid: Id): List<Rental>? {
        val user = db.user.getUserById(uid) ?: throw IllegalStateException("User not found with this id")
        return db.rental.getRentalsOfUser(user)
    }

    fun getRentals(cid: Id, crid: Id, date: Date): List<Rental>? {
        val club = db.club.getClubById(cid) ?: throw IllegalStateException("Club not found with this id $cid")
        val court = db.court.getCourtById(crid) ?: throw IllegalStateException("Court not found with this id $crid")
        return db.rental.getRentals(club, court, date)
    }

    fun getRentalsOfCourt(crid: Id) : List<Rental>? {
        val court = db.court.getCourtById(crid) ?: throw IllegalStateException("Court not found with this id $crid")
        return db.rental.getRentalsOfCourt(court)
    }

    fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int>? {
        val club = db.club.getClubById(cid) ?: throw IllegalStateException("Club not found with this id $cid")
        val court = db.court.getCourtById(crid) ?: throw IllegalStateException("Court not found with this id $crid")
        return db.rental.getAvailableHours(club, court, date)
    }
}

