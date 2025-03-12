package pt.isel.ls.domain

data class UserName (val name : String)

data class User(
    val id : Id,
    val user : UserName,
    val email : Email,
){
    init {
        require(user.name.isNotBlank()) { "User must have a name" }
    }
}