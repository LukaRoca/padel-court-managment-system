package pt.isel.ls.domain


data class User(
    val id : Id,
    val user : Name,
    val email : Email,
){
    init {
        require(user.name.isNotBlank()) { "User must have a name" }
    }
}