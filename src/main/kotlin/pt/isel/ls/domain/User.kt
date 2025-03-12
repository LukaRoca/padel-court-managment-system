package pt.isel.ls.domain

data class User(
    val uid : Id,
    val name: Name,
    val email : Email,
)