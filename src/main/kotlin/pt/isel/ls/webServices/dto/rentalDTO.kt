package pt.isel.ls.webServices.dto

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Date

@Serializable
data class RentalDTO(
        val cid : Int,
        val crid : Int,
        val date : String,
        val time : String,
        val duration : Int,
)

@Serializable
data class RentalListDTO(
    val cid: Int,
    val crid: Int,
    val date: String,
    val time: String
)

@Serializable
data class RentalAvailableHoursRequestDTO(
        val cid: Int,
        val crid: Int,
        val date: String,
        val time: String
)

@Serializable
data class ResponseRentalDto(
    val rid : Int,
)