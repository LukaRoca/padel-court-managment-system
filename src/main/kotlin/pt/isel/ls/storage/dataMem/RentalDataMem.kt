package pt.isel.ls.storage.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.RentalIStorage
import pt.isel.ls.storage.dataMem.ClubDataMem.getClubById
import pt.isel.ls.storage.dataMem.CourtDataMem.getCourtById

object RentalDataMem : RentalIStorage {

    private var rid = 2

    private val rentals = mutableListOf<Rental>()

    override fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token): Rental? {
        val court = getCourtById(crid) ?: return null
        val club = getClubById(cid) ?: return null
        val user = club.owner.user
        val newRental = Rental(Id(rid), date, duration, user, court)
        rid++
        rentals.add(newRental)
        return newRental
    }

    override fun getRentalById(rentalId: Id): Rental? {
        return rentals.find { it.rid == rentalId }
    }

    override fun getRentalList(cid: Id, crid: Id, date: Date): List<Rental>? {
        return rentals.filter { it.user.uid == cid && it.court.id == crid && it.date == date }
    }

    override fun getRentalsOfCourt(crid: Id): List<Rental>? {
        return rentals.filter { it.court.id == crid }
    }

    override fun getRentalsOfUser(uid: Id): List<Rental>? {
        return rentals.filter { it.user.uid == uid }
    }

    override fun getAvailableHours(cid: Id, crid: Id, date: Date, duration: Duration): List<Int> {
        val rentals = getRentalList(cid, crid, date)
        val availableHours = mutableListOf<Int>()
        val occupiedHours = mutableSetOf<Int>()
        rentals?.forEach { rental ->
            val startHour = rental.duration.initDuration
            val endHour = rental.duration.endDuration
            for (hour in startHour until endHour) {
                occupiedHours.add(hour)
            }
        }
        for (hour in 0..24) {
            if (hour !in occupiedHours) {
                availableHours.add(hour)
            }
        }
        return availableHours
    }
}