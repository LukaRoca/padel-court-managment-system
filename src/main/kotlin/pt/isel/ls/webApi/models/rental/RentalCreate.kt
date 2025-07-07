package pt.isel.ls.webApi.models.rental

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.User
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.generateRandomDate
import pt.isel.ls.utils.generateRandomDuration

@Serializable
data class RentalCreate(val date: Date, val duration: Duration, val courtId: Int) {
    companion object Factory {
        fun create(
            date: Date = generateRandomDate(),
            duration: Duration = generateRandomDuration(),
            courtId: Int = 0
        ) : RentalCreate {
            return RentalCreate(date, duration, courtId)
        }
    }
}