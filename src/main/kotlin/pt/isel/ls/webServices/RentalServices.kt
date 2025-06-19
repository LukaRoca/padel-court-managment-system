package pt.isel.ls.webServices

import pt.isel.ls.utlis.PaginatedResult
import pt.isel.ls.domain.*
import pt.isel.ls.utlis.paginateWithInfo
import pt.isel.ls.data.data.Data
import pt.isel.ls.utlis.Date
import pt.isel.ls.utlis.Duration
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Token
import pt.isel.ls.webApi.dto.*
import java.lang.IllegalStateException

class RentalServices (private val db : Data) {

    fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token): Rental? {
        val court = db.court.getCourtById(crid) ?: throw IllegalStateException("Court not found with this id $crid")
        val user = db.user.getUserByToken(token) ?: throw IllegalStateException("User not found with this token")
        val club = db.club.getClubById(cid) ?: throw IllegalStateException("Club not found with this id $cid")
        val availableHours = db.rental.getAvailableHours(club, court, date) ?: throw IllegalStateException("No available hours for this club and court")
        val hoursofNewRental = duration.initDuration..duration.endDuration
        if (!(hoursofNewRental.all { it in hoursofNewRental })) {
            throw IllegalArgumentException("The selected time slot is already occupied")
        }
        return db.rental.createRental(court, date, duration, user)
    }

    fun getRentalById(rentalId: Id): Rental? {
        return db.rental.getRentalById(rentalId)

    }

    fun getRentalsOfUser(uid: Id, limit : Int, skip : Int): PaginatedResult<RentalDetails> {
        val users = db.user.getUserById(uid) ?: throw IllegalStateException("User not found with this id")
        val rentals = db.rental.getRentalsOfUser(users)?.map { rental ->
            RentalDetails(
                rental.rid.id,
                rental.date.value,
                DurationDetails(
                    rental.duration.initDuration,
                    rental.duration.endDuration,
                    rental.duration.hours
                ),
                UserDetails(
                    rental.user.uid.id,
                    rental.user.name.name,
                    rental.user.email.value,
                    rental.user.token.token
                ),
                CourtDetails(
                    rental.court.id.id,
                    rental.court.name.name,
                    ClubDetails(
                        rental.court.club.id.id,
                        rental.court.club.name.name,
                        UserDetails(
                            rental.user.uid.id,
                            rental.user.name.name,
                            rental.user.email.value,
                            rental.user.token.token
                        )
                    )
                ),

            )

        }
        if (rentals != null ) {
            return rentals.paginateWithInfo(limit, skip)
        }
        else throw IllegalArgumentException("No rental found with this $uid")
    }

    fun getRentals(cid: Id, crid: Id, date: Date): List<Rental>? {
        val club = db.club.getClubById(cid) ?: throw IllegalStateException("Club not found with this id $cid")
        val court = db.court.getCourtById(crid) ?: throw IllegalStateException("Court not found with this id $crid")
        return db.rental.getRentals(club, court, date)
    }

    fun getRentalsOfCourt(crid: Id, limit: Int, skip: Int) : PaginatedResult<RentalDetails> {
        val court = db.court.getCourtById(crid) ?: throw IllegalStateException("Court not found with this id $crid")
        val rentals = db.rental.getRentalsOfCourt(court)?.map { rental ->
            RentalDetails(
                rental.rid.id,
                rental.date.value,
                DurationDetails(
                    rental.duration.initDuration,
                    rental.duration.endDuration,
                    rental.duration.hours
                ),
                UserDetails(
                    rental.user.uid.id,
                    rental.user.name.name,
                    rental.user.email.value,
                    rental.user.token.token
                ),
                CourtDetails(
                    rental.court.id.id,
                    rental.court.name.name,
                    ClubDetails(
                        rental.court.club.id.id,
                        rental.court.club.name.name,
                        UserDetails(
                            rental.user.uid.id,
                            rental.user.name.name,
                            rental.user.email.value,
                            rental.user.token.token
                        )
                    )
                ),

                )
        }
        if (rentals != null) {
            return rentals.paginateWithInfo(limit, skip)
        }
        else throw IllegalArgumentException("No rental found with this $crid")
    }

    fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int>? {
        val club = db.club.getClubById(cid) ?: throw IllegalStateException("Club not found with this id $cid")
        val court = db.court.getCourtById(crid) ?: throw IllegalStateException("Court not found with this id $crid")
        return db.rental.getAvailableHours(club, court, date)
    }

    fun deleteRental(rid : Id) : Boolean {
        val rental = db.rental.getRentalById(rid) ?: throw IllegalStateException("Rental not found with this id $rid")
        return db.rental.deleteRental(rental)
    }

    fun updateRental(date: Date, duration: Duration, rid: Id) : RentalDetails? {
        val rental = db.rental.getRentalById(rid) ?: throw IllegalStateException("Rental not found with this id $rid")
        val updatedRental = db.rental.updateRental(date,duration, rental) ?: throw IllegalStateException("Error during update")
        return RentalDetails(updatedRental.rid.id,
        updatedRental.date.value,
            DurationDetails(
                updatedRental.duration.initDuration,
                updatedRental.duration.endDuration,
                updatedRental.duration.hours
            ),
            UserDetails(
                updatedRental.user.uid.id,
                updatedRental.user.name.name,
                updatedRental.user.email.value,
                updatedRental.user.token.token
            ),
            CourtDetails(
                updatedRental.court.id.id,
                updatedRental.court.name.name,
                ClubDetails(
                    updatedRental.court.club.id.id,
                    updatedRental.court.club.name.name,
                    UserDetails(
                        updatedRental.user.uid.id,
                        updatedRental.user.name.name,
                        updatedRental.user.email.value,
                        updatedRental.user.token.token
                    )
                )
            )
        )
    }

    fun getRentalsWithDate(date: Date): List<Rental>? {
        return db.rental.getRentalsWithDate(date)?.map { rental ->
            Rental(
                rental.rid,
                rental.date,
                rental.duration,
                rental.user,
                rental.court
            )
        }
    }

}

