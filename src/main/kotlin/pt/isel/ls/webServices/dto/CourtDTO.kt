package pt.isel.ls.webServices.dto


import kotlinx.serialization.Serializable

@Serializable
data class CourtDTO(
    val name : String,
    val id : Int,
)

@Serializable
data class ResponseCourtDto(
    val crid : Int
)