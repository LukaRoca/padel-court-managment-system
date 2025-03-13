package pt.isel.ls.webApi.user

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Email
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name

@Serializable
data class UserDTO(
    val name : String,
    val email : String,
)