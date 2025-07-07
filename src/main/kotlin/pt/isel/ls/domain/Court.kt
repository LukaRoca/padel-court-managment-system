package pt.isel.ls.domain

import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.validateClubName
import pt.isel.ls.utils.validateName

data class Court(
    val id: Id,
    val name: Name,
    val club: Id,
) {
    init {
        validateName(name.name, 3, 100, "Court")
    }
}