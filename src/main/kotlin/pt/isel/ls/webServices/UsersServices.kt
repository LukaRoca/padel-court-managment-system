package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.IStorage
import pt.isel.ls.storage.iStorage.UserIStorage

class UserServices (private val db : IStorage) {

    fun getUserById(userId: Id): User? {
        return db.user.getUserById(userId)
    }

    fun createUser(name: Name, email: Email): User {
        return db.user.createUser(name,email)
    }

    fun getUserByToken(token: Token): User? {
        return db.user.getUserByToken(token)
    }

    fun getAllUsers(): List<User> {
        return db.user.getAllUsers()
    }

}