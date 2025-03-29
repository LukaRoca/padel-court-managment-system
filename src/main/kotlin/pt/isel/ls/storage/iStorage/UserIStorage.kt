package pt.isel.ls.storage.iStorage

import pt.isel.ls.domain.*

interface UserIStorage {

    fun createUser(name: Name, email: Email) : User
    fun getUserById(userId: Id): User?
    fun getUserByToken(token: Token): User?
}