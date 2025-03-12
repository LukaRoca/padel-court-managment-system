package pt.isel.ls.webApi.routes.user

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Email
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name

@Serializable
data class UserDTO(
    val id : Id,
    val name : Name,
    val email : Email,
)