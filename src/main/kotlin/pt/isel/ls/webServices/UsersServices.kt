package pt.isel.ls.webServices

import pt.isel.ls.utils.PaginatedResult
import pt.isel.ls.domain.*
import pt.isel.ls.utils.paginateWithInfo
import pt.isel.ls.data.Data
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token
import pt.isel.ls.webApi.dto.UserDetails

class UserServices (private val db : pt.isel.ls.data.Data) {

    fun getUserById(userId: Id): User? {
        return db.user.getUserById(userId)
    }

    fun createUser(name: Name, email: Email, password : Password): User {
        val existingEmail = db.user.getAllUsers().find { it.email == email }
        if (existingEmail != null) {
            throw IllegalArgumentException("Already exists a user with this email:  '${email.value}'")
        }
        println(password)
        return db.user.createUser(name,email, password)
    }

    fun getUserByToken(token: Token): User? {
        return db.user.getUserByToken(token)
    }

    fun getAllUsers(limit : Int, skip : Int): PaginatedResult<UserDetails> {
        val listUsers = db.user.getAllUsers().map {
            UserDetails(it.uid.id, it.name.name, it.email.value, it.token.token)
        }
        return listUsers.paginateWithInfo(limit, skip)
    }
    fun loginUser(email: Email, password: Password): User {
        val user = db.user.getAllUsers().find { it.email == email }
            ?: throw NoSuchElementException("No user found with email: ${email.value}")
        if (!password.verify(user.password.value)) {
            throw IllegalArgumentException("Invalid password for user with email: ${email.value}")
        }
        return user
    }

}