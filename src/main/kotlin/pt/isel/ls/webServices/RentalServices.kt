package pt.isel.ls.webServices

import pt.isel.ls.domain.Rental
import pt.isel.ls.storage.DataMem

object RentalServices {
    fun createRental(cid: Int, crid: Int, date: String, duration: Int ): Rental? {
        return DataMem.createRental(cid, crid, date, duration)

    }


}