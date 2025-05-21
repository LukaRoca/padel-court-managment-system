package pt.isel.ls.storage.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.RentalIStorage
import pt.isel.ls.storage.dataMem.ClubDataMem.getClubById
import pt.isel.ls.storage.dataMem.CourtDataMem.getCourtById

object RentalDataMem : RentalIStorage {

    private var rid = 2

    private val rentals = mutableListOf<Rental>()

    override fun createRental(court: Court, date: Date, duration: Duration, user: User): Rental? {
        val newRental = Rental(Id(rid), date, duration, user, court)
        rid++
        rentals.add(newRental)
        return newRental
    }

    override fun getRentalById(rentalId: Id): Rental? {
        return rentals.find { it.rid == rentalId }
    }

    override fun getRentalsOfUser(user: User): List<Rental>? {
        return rentals.filter { it.user == user }
    }

    override fun getRentalsOfCourt(court: Court): List<Rental>? {
        return rentals.filter { it.court == court }
    }

    override fun getRentals(club: Club, court: Court, date: Date) : List<Rental>? {
        return rentals.filter { it.court == court && it.date == date }
    }


    override fun getAvailableHours(club: Club, court: Court, date: Date): List<Int> {
        val rentals = getRentals(club, court, date)
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

    override fun updateRental(date: Date, duration: Duration, rental: Rental): Rental? {
        TODO("Not yet implemented")
    }

    override fun deleteRental(rental: Rental): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRentalsWithDate(date: Date): List<Rental> {
        TODO("Not yet implemented")
    }
}

