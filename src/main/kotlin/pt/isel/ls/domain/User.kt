package pt.isel.ls.domain

data class Uid (val id : Int) {init {
    require(id > 0) { "id must be positive" }
}}
data class UserName (val name : String)
data class Email (val email : String) {init {
    require("@" in email) { "Email must have @ in it"  }
}}

data class User(
    val id : Uid,
    val user : UserName,
    val email : Email,
){
    init {
        require(user.name.isNotBlank()) { "User must have a name" }
    }
}