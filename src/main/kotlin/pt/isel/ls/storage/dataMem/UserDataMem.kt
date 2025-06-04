package pt.isel.ls.storage.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.UserIStorage
import java.util.*

object UserDataMem : UserIStorage {

    private val users = mutableListOf<User>()
    private var uid = 2

    override fun createUser(name: Name, email: Email,password: Password) : User {
        val token = UUID.randomUUID().toString()
        val newUser = User(Id(uid), name, email, Token(token), password)
        uid++
        users.add(newUser)
        return newUser
    }
    override fun getUserById(userId: Id): User? {
        return users.find { it.uid == userId }
    }
    override fun getUserByToken(token: Token): User? {
        return users.find { it.token == token }
    }
    override fun getAllUsers(): List<User> {
        return users
    }
}