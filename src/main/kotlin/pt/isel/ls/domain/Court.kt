package pt.isel.ls.domain

import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.validateClubName
import pt.isel.ls.utlis.validateName

data class Court(
    val id: Id,
    val name: Name,
    val club: Club,
) {
    init {
        validateName(name.name, 3, 100, "Court")
        validateClubName(club.name.name, 3, 100, "Club")
    }
}