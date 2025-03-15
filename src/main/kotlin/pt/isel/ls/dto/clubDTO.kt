package pt.isel.ls.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClubDTO(
    val name : String,
)

@Serializable
data class ResponseClubDto(
    val cid : Int,
)