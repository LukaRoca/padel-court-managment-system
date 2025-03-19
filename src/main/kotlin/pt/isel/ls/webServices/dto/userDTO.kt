package pt.isel.ls.webServices.dto

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