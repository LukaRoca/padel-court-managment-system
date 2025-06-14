package pt.isel.ls.domain

import pt.isel.ls.utlis.Email
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.Password
import pt.isel.ls.utlis.Token
import pt.isel.ls.utlis.validateName

data class User(
    val uid: Id,
    val name: Name,
    val email: Email,
    val token: Token,
    val password: Password
){
    init {
        validateName(name.name, 3, 50, "User")
        if (!password.value.startsWith("\$2a\$")) {
            require(password.value.length in 8..50) { "Password must be between 8 and 50 characters" }
        }

    }
}