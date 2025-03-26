package pt.isel.ls.storage.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.UserIStorage
import java.sql.Connection
import java.util.*

class UserDataPostgres (private val connection : Connection) : UserIStorage {
    private var uid = 2

    override fun createUser(name: Name, email: Email) : User {
        val token = UUID.randomUUID()
        val sql = "INSERT INTO users(uid, token, name, email) VALUES (?, ?, ?,?)"
        connection.prepareStatement(sql).use {
            it.setInt(1, uid)
            it.setObject(2, token)
            it.setString(3, name.name)
            it.setString(4, email.value)
        }
        uid++
        return User(Id(uid), name, email, Token(token.toString()))
    }

    override fun getUserById(userId: Id): User? {
        TODO()
    }

    override fun getUserByToken(token: Token): User? {
        TODO()
    }

}