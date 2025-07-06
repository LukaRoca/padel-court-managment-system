package pt.isel.ls.data.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.data.data.RentalData
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Id

class RentalDataMem(private val rentals: DataMemMap<Rental> = DataMemMap()) : RentalData {

    override fun createRental(court: Court, date: Date, duration: Duration, user: User): Rental? {
        val newRental = Rental(Id(rentals.nextId.get()), date, duration, user, court)
        rentals.map[rentals.nextId.get()] = newRental
        return newRental
    }

    override fun getRentalById(rentalId: Id): Rental? = rentals.map[rentalId.id]

    override fun getRentalsOfUser(user: User): List<Rental>? = rentals.map.values.filter { it.user == user }

    override fun getRentalsOfCourt(court: Court): List<Rental>? = rentals.map.values.filter { it.court == court }

    override fun getRentals(club: Club, court: Court, date: Date) : List<Rental>? = rentals.map.values.filter { it.court == court && it.date == date }

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
        val updatedRental = rentals.map[rental.rid.id]?.copy(date = date, duration = duration)
        if (updatedRental != null) {
            rentals.map[rental.rid.id] = updatedRental
        }
        return updatedRental
    }

    override fun deleteRental(rental: Rental): Boolean = rentals.map.remove(rental.rid.id) != null

    override fun getRentalsWithDate(date: Date): List<Rental> = rentals.map.values.filter { it.date == date }
}

