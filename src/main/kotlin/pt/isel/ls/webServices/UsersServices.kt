package pt.isel.ls.webServices

import pt.isel.ls.PaginatedResult
import pt.isel.ls.domain.*
import pt.isel.ls.paginateWithInfo
import pt.isel.ls.storage.iStorage.IStorage
import pt.isel.ls.storage.iStorage.UserIStorage
import pt.isel.ls.webApi.dto.UserDetails

class UserServices (private val db : IStorage) {

    fun getUserById(userId: Id): User? {
        return db.user.getUserById(userId)
    }

    fun createUser(name: Name, email: Email): User {
        val existingEmail = db.user.getAllUsers().find { it.email == email }
        if (existingEmail != null) {
            throw IllegalArgumentException("Already exists a user with this email:  '${email.value}'")
        }
        return db.user.createUser(name,email)
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

}