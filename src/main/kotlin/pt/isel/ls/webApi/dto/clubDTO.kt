package pt.isel.ls.webApi.dto

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Id

@Serializable
data class ClubInput(
    val name : String,
)

@Serializable
data class ClubOutput(
    val cid : Id
)