package pt.isel.ls.webServices

import pt.isel.ls.domain.Date
import pt.isel.ls.domain.Rental
import pt.isel.ls.storage.DataMem

object RentalServices {
    fun createRental(cid: Int, crid: Int, date: Date, duration: Int, token: String ): Rental? {
        return DataMem.createRental(cid, crid, date, duration, token)

    }

    fun getRentalById(rentalId: Int): Rental? {
        return DataMem.getRentalById(rentalId)

    }

    fun getRentalList(cid: Int, crid: Int, date: Date): List<Rental>? {
        return DataMem.getRentalList(cid, crid, date)
    }

    fun getRentalsOfUser(cid: Int): List<Rental> {
        return DataMem.getRentalsOfUser(cid)

    }

    fun getAvailableHours(cid: Int, crid: Int, date: Date): List<Int> {
        return DataMem.getAvailableHours(cid, crid, date)

    }

}