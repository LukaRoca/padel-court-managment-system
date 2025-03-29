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
        require(name.name.isNotBlank()) { "User must have a name" }
        require(email.value.isNotBlank() && email.value.find { it == '@' } != null) { "User must have a valid email" }
        require(uid.id > 0) { "User ID must be a positive number" }
        require(name.name.length in 3..50) { "User name must be between 3 and 50 characters" }
        require(name.name.all { it.isLetter() || it.isWhitespace() }) { "User name must contain only letters and spaces" }
        require(token.token.length >= 20) { "Token must be at least 20 characters long" }
    }
}