package pt.isel.ls.webApi.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserInput(
    val name : String,
    val email : String,
    val password : String
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

@Serializable
data class UserLoginInput(
    val email: String,
    val password: String
)

@Serializable
data class UserLoginOutput(
    val id: Int,
    val token: String
)