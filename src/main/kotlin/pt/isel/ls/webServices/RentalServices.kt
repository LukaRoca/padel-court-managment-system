package pt.isel.ls.webServices

import pt.isel.ls.domain.Date
import pt.isel.ls.domain.Duration
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Rental
import pt.isel.ls.storage.DataMem

object RentalServices {
    fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: String ): Rental? {
        return DataMem.createRental(cid, crid, date, duration, token)

    }

    fun getRentalById(rentalId: Id): Rental? {
        return DataMem.getRentalById(rentalId)

    }

    fun getRentalList(cid: Id, crid: Id, date: Date): List<Rental> {
        return DataMem.getRentalList(cid, crid, date)
    }

    fun getRentalsOfUser(cid: Id): List<Rental> {
        return DataMem.getRentalsOfUser(cid)

    }

    fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int> {
        return DataMem.getAvailableHours(cid, crid, date)

    }

}