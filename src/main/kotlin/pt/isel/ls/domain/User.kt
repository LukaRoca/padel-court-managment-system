package pt.isel.ls.domain

data class User(
    val uid : Id,
    val name : Name,
    val email : Email,
    val token : Token
){
    init {
        validateName(name.name, 3, 50, "User")
    }
}