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

}