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

/*
@Serializable
data class RentalListDTO(
    val cid: Int,
    val crid: Int,
    val date: String,
    val initDuration : Int,
    val endDuration : Int,
)

 */

@Serializable
data class RentalAvailableHoursRequestDTO(
        val cid: Id,
        val crid: Id,
        val date: Date,
        val initDuration : Int,
        val endDuration : Int,
)

@Serializable
data class RentalOutput(
    val rid : Id,
)