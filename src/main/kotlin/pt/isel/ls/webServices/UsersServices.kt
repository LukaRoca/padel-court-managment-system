package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.UserIStorage

class UserServices (private val db : UserIStorage) {

    fun getUserById(userId: Id): User? {
        return db.getUserById(userId)
    }

    fun createUser(name: Name, email: Email): User {
        val existingUsers = db.getAllUsers()
        if (existingUsers.any { it.name.name == name.name }) {
            throw IllegalArgumentException("A user with the same name already exists")
        }
        if (existingUsers.any { it.email == email }) {
            throw IllegalArgumentException("A user with the same email already exists")
        }
        return db.createUser(name, email)
    }

    fun getUserByToken(token: Token): User? {
        return db.getUserByToken(token)
    }

    fun getAllUsers(): List<User> {
        return db.getAllUsers()
    }
}