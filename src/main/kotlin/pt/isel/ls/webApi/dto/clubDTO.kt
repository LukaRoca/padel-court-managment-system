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
