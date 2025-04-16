package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class Club(
    val id : Id,
    val name : Name,
    val owner : Owner,
) {
    init {
        validateName(name.name, 3, 100, "Club")
    }
}


