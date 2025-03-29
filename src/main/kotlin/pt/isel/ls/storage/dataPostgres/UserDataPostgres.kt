package pt.isel.ls.storage.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.UserIStorage
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class UserDataPostgres (private val connection : Connection) : UserIStorage {
    override fun createUser(name: Name, email: Email) : User {
        val token = UUID.randomUUID()
        val sql = "INSERT INTO users(token, name, email) VALUES (?, ?,?)"
        val statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS).apply {
            setObject(1, token)
            setString(2, name.name)
            setString(3, email.value)
        }
        //Fazer função para tratar desta cena
        if (statement.executeUpdate() == 0) {
            throw SQLException("Error while creating a new user.")
        }
        val keys = statement.generatedKeys
        keys.next()
        return User(Id(keys.getInt(1)), name, email, Token(token.toString()))
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
        val sql = "SELECT * FROM users WHERE token = ?"
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