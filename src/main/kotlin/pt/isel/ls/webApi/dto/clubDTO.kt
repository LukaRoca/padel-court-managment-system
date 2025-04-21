package pt.isel.ls.webApi.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClubInput(
    val name : String,
)

@Serializable
data class ClubOutput(
    val id : Int
)

@Serializable
data class ClubDetails(
    val id : Int,
    val name : String,
    val owner : UserDetails
)