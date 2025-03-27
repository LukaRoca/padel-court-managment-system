package pt.isel.ls.storage.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.RentalIStorage
import pt.isel.ls.storage.dataMem.ClubDataMem.getClubById
import pt.isel.ls.storage.dataMem.CourtDataMem.getCourt

object RentalDataMem : RentalIStorage {

    private var rid = 2

    private val rentals = mutableListOf<Rental>()

    override fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token): Rental? {
        val court = getCourt(crid) ?: return null
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

    override fun getRentalsOfUser(uid: Id): List<Rental>? {
        return rentals.filter { it.user.uid == uid }
    }

    override fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int> {
        val rentals = getRentalList(cid, crid, date)
        val availableHours = mutableListOf<Int>()
        val occupiedHours = mutableSetOf<Int>()

        rentals?.forEach { rental ->
            // Get the start hour from the rental duration
            val startHour = rental.duration.initDuration
            // Calculate the end hour by adding the duration hours
            val endHour = rental.duration.endDuration

            // Mark all hours in this rental as occupied
            for (hour in startHour until endHour) {
                occupiedHours.add(hour)
            }
        }

        // Check all hours from 7 AM to 9 PM (21:00)
        for (hour in 7..21) {
            // If the hour is not in occupied hours, it's available
            if (hour !in occupiedHours) {
                availableHours.add(hour)
            }
        }

        return availableHours
    }


}