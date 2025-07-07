package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.data.Data
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Id
import pt.isel.ls.webApi.models.rental.RentalCreate
import pt.isel.ls.webApi.models.rental.RentalDetails
import pt.isel.ls.webApi.models.rental.RentalListResponse
import pt.isel.ls.webApi.models.rental.RentalResponse
import java.lang.IllegalStateException
import java.util.UUID

class RentalServices (private val db : Data) : ServicesSchema(db){

    fun createRental(rentalCreate: RentalCreate, court : Int, token: UUID): RentalResponse =
        withAuthorization(token){
            val court = db.court.getCourtById(Id(court)) ?: throw NoSuchElementException("No court found with this Id $court")
            val user = db.user.getUserByToken(token) ?: throw NoSuchElementException("No user found with this token")
            val club = db.club.getClubById(court.club)
            val duration = db.rental.getAvailableHours(court, rentalCreate.date) ?: throw NoSuchElementException("No available hours found")
            val hoursOfNewRental = rentalCreate.duration.initDuration..rentalCreate.duration.endDuration
            if (!(duration.all { it in hoursOfNewRental })) {
                throw IllegalArgumentException("The selected time slot is already occupied")
            }
            val rental = db.rental.createRental(rentalCreate, court.id, user.uid)
            return@withAuthorization RentalResponse(rental)
        }


    fun getRentalById(
        rentalId: Id,
        token: UUID
    ) : Rental =
        withAuthorization(token) {
            val rentals = db.rental.getRentalById(rentalId)
                ?: throw NoSuchElementException("No Rental found with this id")
            return@withAuthorization Rental(rentals.rid, rentals.date, rentals.duration, rentals.user, rentals.court)
        }



    fun getRentalsOfUser(
        uid: Id,
        token: UUID,
        limit : Int,
        skip : Int
    ): RentalListResponse =
        withAuthorization(token) {
            val rentals = db.rental.getRentalsOfUser(uid, limit,skip)
            return@withAuthorization RentalListResponse(rentals)
        }


    fun getRentalsOfCourt(
        crid: Id,
        token: UUID,
        limit: Int,
        skip: Int
    ): RentalListResponse =
        withAuthorization(token) {
            val rentals = db.rental.getRentalsOfCourt(crid, limit, skip)
            return@withAuthorization RentalListResponse(rentals)
        }

    fun getRentalsWithDate(
        date: Date,
        token : UUID,
    ): List<Rental> =
        withAuthorization(token) {
            val rentals = db.rental.getRentalsWithDate(date)
            return@withAuthorization rentals
        }

    fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int>? {
        val club = db.club.getClubById(cid) ?: throw IllegalStateException("Club not found with this id $cid")
        val court = db.court.getCourtById(crid) ?: throw IllegalStateException("Court not found with this id $crid")
        return db.rental.getAvailableHours(court, date)
    }

    fun deleteRental(rid : Id, token:UUID) : Boolean = withAuthorization(token) {
        val rental = db.rental.getRentalById(rid) ?: throw IllegalStateException("Rental not found with this id $rid")
        return@withAuthorization db.rental.deleteRental(rental.rid)
    }

    fun updateRental(date: Date, duration: Duration, rid: Id) : Boolean {
        val rental = db.rental.getRentalById(rid) ?: throw IllegalStateException("Rental not found with this id $rid")
        val updatedRental = db.rental.updateRental(date,duration, rid)
        return updatedRental
    }


    fun getClubById(clubId: Id, token: UUID) = withAuthorization(token) {
        db.club.getClubById(clubId)
    }

    fun getCourtById(courtId: Id, token: UUID) = withAuthorization(token) {
        db.court.getCourtById(courtId)
    }

    fun getUserById(userId: Id, token: UUID) = withAuthorization(token) {
        db.user.getUserById(userId)
    }
}

