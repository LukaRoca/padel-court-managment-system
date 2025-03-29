package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class Court(
    val id: Id,
    val name: Name,
    val club: Club,
) {
    init {
        require(name.name.isNotBlank()) { "Name must not be empty" }
        require(name.name.length in 3..100) { "Court name must be between 3 and 100 characters" }
        require(name.name.all { it.isLetterOrDigit() || it.isWhitespace() }) { "Court name must contain only letters, numbers, and spaces" }
        require(club.name.name.isNotBlank()) { "Club name must not be empty" }
        require(id.id > 0) { "Id must be greater than zero." }
    }
}