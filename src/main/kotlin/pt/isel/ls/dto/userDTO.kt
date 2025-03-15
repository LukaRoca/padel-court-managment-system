package pt.isel.ls.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val name : String,
    val email : String,
)

@Serializable
data class ResponseUserDto(
    val uid : Int,
    val token : String,
)