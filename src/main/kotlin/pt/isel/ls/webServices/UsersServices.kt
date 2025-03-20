package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.UserIStorage

class UserServices (private val db : UserIStorage) {

    fun getUserById(userId: Id): User? {
        return db.getUserById(userId)
    }

    fun createUser(name: Name, email: Email): User {
        return db.createUser(name,email)
    }

    fun getUserByToken(token: Token): User? {
        return db.getUserByToken(token)
    }

}