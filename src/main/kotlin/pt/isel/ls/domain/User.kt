package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid : Id,
    val name : Name,
    val email : Email,
    val token : Token
){
    init {
        validateName(name.name, 3, 50, "User")
    }
}