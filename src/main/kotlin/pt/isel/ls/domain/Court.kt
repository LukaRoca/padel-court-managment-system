package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class Court(
    val id : Id,
    val name : Name,
    val club: Club,
) {
    init {
        require(name.name.isNotBlank()) { "Name must not be empty" }
        require(club.name.name.isNotBlank()) { "Club name must not be empty" }
    }
}