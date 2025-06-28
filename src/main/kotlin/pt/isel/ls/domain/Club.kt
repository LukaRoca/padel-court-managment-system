package pt.isel.ls.domain

import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Owner
import pt.isel.ls.utils.validateName

data class Club(
    val id : Id,
    val name : Name,
    val owner : Owner
) {
    init {
        validateName(name.name, 3, 100, "Club")
    }
}


