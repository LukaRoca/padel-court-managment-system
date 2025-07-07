package pt.isel.ls.data.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.data.RentalData
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Id
import pt.isel.ls.webApi.models.rental.RentalCreate
import kotlin.collections.get
import kotlin.collections.remove
import kotlin.text.get
import kotlin.text.set

class RentalDataMem(private val rentals: DataMemMap<Rental> = DataMemMap()) : pt.isel.ls.data.RentalData {

    override fun createRental(rentalCreate: RentalCreate, court: Id, user: Id): Rental {
        val newRental = Rental(
            Id(rentals.nextId.get()),
            rentalCreate.date,
            rentalCreate.duration,
            user,
            court
        )
        rentals.map[rentals.nextId.get()] = newRental
        return newRental
    }

    override fun getRentalById(rentalId: Id): Rental? = rentals.map[rentalId.id]

    override fun getRentalsOfUser(user: Id): List<Rental> = rentals.map.values.filter { it.user == user }

    override fun getRentalsOfCourt(court: Id): List<Rental> =
        rentals.map.values.filter { it.court == court }

    override fun getRentals(): List<Rental> = rentals.map.values.toList()

    override fun getAvailableHours(court: Court, date: Date): List<Int>? {
        val rentals = getRentals()
        val availableHours = mutableListOf<Int>()
        val occupiedHours = mutableSetOf<Int>()
        rentals.forEach { rental ->
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

    override fun updateRental(date: Date, duration: Duration, rental: Id): Boolean {
        val existing = rentals.map[rental.id]
        if (existing != null) {
            rentals.map[rental.id] = existing.copy(date = date, duration = duration)
            return true
        }
        return false
    }

    override fun deleteRental(rental: Id): Boolean =
        rentals.map.remove(rental.id) != null

    override fun getRentalsWithDate(date: Date): List<Rental> = rentals.map.values.filter { it.date == date }
}

