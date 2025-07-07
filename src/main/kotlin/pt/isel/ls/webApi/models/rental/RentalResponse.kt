package pt.isel.ls.webApi.models.rental

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Rental
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration

@Serializable
class RentalResponse private constructor(
    val id : Int,
    val date: Date,
    val duration : Duration
) {
    companion object {
        operator fun invoke(rental: Rental): RentalResponse {
            return RentalResponse(
                rental.rid.id,
                rental.date,
                rental.duration
            )
        }
    }
}