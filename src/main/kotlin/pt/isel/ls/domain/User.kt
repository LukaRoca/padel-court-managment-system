package pt.isel.ls.domain

import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token
import pt.isel.ls.utils.validateName

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