package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.IStorage

class UserServices (private val db : IStorage) {

    fun getUserById(userId: Id): User? {
        return db.user.getUserById(userId)
    }

    fun createUser(name: Name, email: Email): User {
        val existingUsers = db.user.getAllUsers()
        if (existingUsers.any { it.name.name == name.name }) {
            throw IllegalArgumentException("A user with the same name already exists")
        }
        if (existingUsers.any { it.email == email }) {
            throw IllegalArgumentException("A user with the same email already exists")
        }
        return db.user.createUser(name, email)
    }

    fun getUserByToken(token: Token): User? {
        return db.user.getUserByToken(token)
    }

    fun getAllUsers(): List<User> {
        return db.user.getAllUsers()
    }

}