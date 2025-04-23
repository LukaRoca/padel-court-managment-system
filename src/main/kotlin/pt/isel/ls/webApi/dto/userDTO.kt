package pt.isel.ls.webApi.dto

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Token
import pt.isel.ls.domain.User

@Serializable
data class UserInput(
    val name : String,
    val email : String,
)

@Serializable
data class UserOutput(
    val uid : Int,
    val token : String
)

@Serializable
data class UserDetails(
    val id : Int,
    val name : String,
    val email : String,
    val token : String
)