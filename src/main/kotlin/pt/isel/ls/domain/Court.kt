package pt.isel.ls.domain

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