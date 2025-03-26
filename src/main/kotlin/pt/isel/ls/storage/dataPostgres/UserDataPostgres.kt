package pt.isel.ls.storage.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.UserIStorage
import java.sql.Connection
import java.util.*


class UserDataPostgres (private val connection : Connection) : UserIStorage {
    private var uid = 1

    override fun createUser(name: Name, email: Email) : User {
        val token = UUID.randomUUID()
        val sql = "INSERT INTO users(uid, token, name, email) VALUES (?, ?, ?,?)"
        connection.prepareStatement(sql).use {
            it.setInt(1, uid)
            it.setObject(2, token)
            it.setString(3, name.name)
            it.setString(4, email.value)
            it.executeUpdate()
        }
        return User(Id(uid++), name, email, Token(token.toString()))
    }

    override fun getUserById(userId: Id): User? {
        val sql = "SELECT uid, name, email, token FROM users WHERE uid = ?"

        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, userId.id)
            stmt.executeQuery().use { result ->
                if (result.next()) {
                    return User(
                        Id(result.getInt("uid")),
                        Name(result.getString("name")),
                        Email(result.getString("email")),
                        Token(result.getString("token"))
                    )
                }
            }
        }

        return null
    }

    override fun getUserByToken(token: Token): User? {
        val sql = "SELECT token FROM users WHERE token = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, token.token)
            stmt.executeQuery().use { result ->
                if (result.next()) {
                    return User(
                        Id(result.getInt("uid")),
                        Name(result.getString("name")),
                        Email(result.getString("email")),
                        Token(result.getString("token"))
                    )
                }
            }
        }
        return null
    }

}