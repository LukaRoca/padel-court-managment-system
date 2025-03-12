package pt.isel.ls.domain

data class User(
    val id : Uid,
    val user : UserName,
    val email : Email,
){
    init {
        require(user.name.isNotBlank()) { "User must have a name" }
    }
}