package pt.isel.ls.webApi.dto

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Id

@Serializable
data class UserDTO(
    val name : String,
    val email : String,
)

@Serializable
data class ResponseUserDto(
    val uid : Id,
    val token : String,
)