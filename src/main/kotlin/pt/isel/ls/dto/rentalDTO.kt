package pt.isel.ls.dto

import kotlinx.serialization.Serializable

@Serializable
data class RentalDTO(
    val cid : Int,
    val crid : Int,
    val date : String,
    val duration : Int,
)

@Serializable
data class RentalListDTO(
    val cid: Int,
    val crid: Int,
    val date: String,
)

@Serializable
data class ResponseRentalDto(
    val rid : Int,
)