package pt.isel.ls.webApi.models.rental

import pt.isel.ls.domain.Court
import pt.isel.ls.domain.Rental
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.webApi.models.court.CourtDetails


class RentalDetails private constructor(val id: Int, val date: Date, val duration: Duration, val court: Int, val user : Int){
    companion object {
        operator fun invoke(rental: Rental) : RentalDetails{
            return RentalDetails(rental.rid.id,
                rental.date,
                rental.duration,
                rental.user.id,
                rental.court.id
                )
        }
    }

    override operator fun equals(other : Any?) : Boolean {

        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RentalDetails

        if (id != other.id) return false
        if (date != other.date) return false
        if (duration != other.duration) return false
        if (user != other.user) return false
        if (court != other.court) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + date.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + court
        return result
    }
}