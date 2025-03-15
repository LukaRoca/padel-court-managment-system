package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class Club(
    val id : Id,
    val name : Name,
    val owner : Owner,
) {
    init {
        require(name.name.isNotBlank()) { "Name must not be empty" }
    }
}


