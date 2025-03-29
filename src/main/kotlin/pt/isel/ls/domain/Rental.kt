package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class Rental (val rid : Id, val date : Date, val duration : Duration, val user : User, val court : Court) {
    init {
        require(rid.id > 0) { "Rental ID must be greater than zero." }
        require(duration.initDuration in 0..23) { "Initial duration must be between 0 and 23." }
        require(duration.endDuration in 1..24) { "End duration must be between 1 and 24." }
        require(duration.endDuration > duration.initDuration) { "End duration must be greater than initial duration." }
        require(user.uid.id > 0) { "User ID must be greater than zero." }
        require(court.id.id > 0) { "Court ID must be greater than zero." }
    }
}
