package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid : Id ,
    val user : Name,
    val email : Email,
){
    init {
        require(user.name.isNotBlank()) { "User must have a name" }
    }
}