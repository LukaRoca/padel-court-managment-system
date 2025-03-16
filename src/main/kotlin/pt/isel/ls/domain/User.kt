package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid : Id,
    val name : Name,
    val email : Email,
){
    init {
        require(name.name.isNotBlank()) { "User must have a name" }
    }
}