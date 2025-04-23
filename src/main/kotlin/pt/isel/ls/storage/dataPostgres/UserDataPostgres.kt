package pt.isel.ls.storage.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.UserIStorage
import pt.isel.ls.webApi.dto.UserDetails
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.*
import javax.sql.DataSource

class UserDataPostgres (private val dataSource : DataSource) : UserIStorage {
    override fun createUser(name: Name, email: Email) : User {
        val token = UUID.randomUUID()
        val sql = "INSERT INTO users(token, name, email) VALUES (?, ?,?)"
        dataSource.connection.use {
            val stmt = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            stmt.setObject(1, token)
            stmt.setString(2, name.name)
            stmt.setString(3, email.value)
            if (stmt.executeUpdate() == 0) {
                throw SQLException("Error while creating a new user.")
            }
            val keys = stmt.generatedKeys
            keys.next()
            return User(Id(keys.getInt(1)), name, email, Token(token.toString()))
        }
    }
    override fun getUserById(userId: Id): User? {
        val sql = "SELECT uid, name, email, token FROM users WHERE uid = ?"
        dataSource.connection.use {
            val stmt = it.prepareStatement(sql)
            stmt.setInt(1, userId.id)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                return User(
                    Id(rs.getInt("uid")),
                    Name(rs.getString("name")),
                    Email(rs.getString("email")),
                    Token(rs.getString("token"))
                )
            }
        }
        return null
    }

    override fun getUserByToken(token: Token): User? {
        val sql = "SELECT * FROM users WHERE users.token = ?"
        dataSource.connection.use {
            val stmt = it.prepareStatement(sql)
            stmt.setString(1, token.token)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                return User(
                    Id(rs.getInt("uid")),
                    Name(rs.getString("name")),
                    Email(rs.getString("email")),
                    Token(rs.getString("token"))
                )
            }
        }
        return null
    }

    override fun getAllUsers(): List<User> {
        val sql = "SELECT * FROM users"
        val users = mutableListOf<User>()
        dataSource.connection.use {
            val stmt = it.prepareStatement(sql)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                users.add(
                    User(
                        Id(rs.getInt("uid")),
                        Name(rs.getString("name")),
                        Email(rs.getString("email")),
                        Token(rs.getString("token"))
                    )
                )
            }
        }
        return users
    }
}