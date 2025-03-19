package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.RentalDataMem

object RentalServices {

    fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token ): Rental? {
        return RentalDataMem.createRental(cid, crid, date, duration, token)

    }

    fun getRentalById(rentalId: Id): Rental? {
        return RentalDataMem.getRentalById(rentalId)

    }

    fun getRentalList(cid: Id, crid: Id, date: Date): List<Rental> {
        return RentalDataMem.getRentalList(cid, crid, date)
    }

    fun getRentalsOfUser(cid: Id): List<Rental> {
        return RentalDataMem.getRentalsOfUser(cid)

    }

    fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int> {
        return RentalDataMem.getAvailableHours(cid, crid, date)

    }

}

