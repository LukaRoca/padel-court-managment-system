package pt.isel.ls.webApi.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClubDTO(
    val name : String,
)

@Serializable
data class ResponseClubDto(
    val cid : Int,
)