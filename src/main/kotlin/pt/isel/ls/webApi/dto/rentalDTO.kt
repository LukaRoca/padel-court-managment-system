package pt.isel.ls.webApi.dto

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Date
import pt.isel.ls.domain.Duration
import pt.isel.ls.domain.Id

@Serializable
data class RentalInput(
    val cid : Int,
    val crid : Int,
    val date : String,
    val initDuration : Int,
    val endDuration : Int,
)

@Serializable
data class RentalOutput(
    val id : Int
)

@Serializable
data class DurationDetails(
    val initDuration : Int,
    val endDuration : Int,
    val hours : Int
)

@Serializable
data class RentalDetails(
    val id: Int,
    val date: String,
    val duration: DurationDetails,
    val user : UserDetails,
    val court : CourtDetails
)
