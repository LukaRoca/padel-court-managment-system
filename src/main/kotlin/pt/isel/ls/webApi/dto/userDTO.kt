package pt.isel.ls.webApi.dto

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Token

@Serializable
data class UserInput(
    val name : String,
    val email : String,
)

@Serializable
data class UserOutput(
    val uid : Id,
    val token : Token,
)