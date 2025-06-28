package pt.isel.ls.data.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.data.data.UserData
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token
import pt.isel.ls.utils.postgres.toUser
import java.sql.SQLException
import java.sql.Statement
import java.util.*
import javax.sql.DataSource

class UserDataPostgres (private val dataSource : DataSource) : UserData {
    override fun createUser(name: Name, email: Email, password: Password) : User =
        dataSource.connection.use {
            val token = UUID.randomUUID()
            val hash = password.hash()
            val sql = "INSERT INTO users(token, name, email, password) VALUES (?, ?,?,?)"
            val stmt = it.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS
            ).apply {
                setObject(1, token)
                setString(2, name.name)
                setString(3, email.value)
                setString(4, hash)
            }

            if (stmt.executeUpdate() == 0) {
                throw SQLException("Error while creating a new user.")
            }
            val keys = stmt.generatedKeys

            if (keys.next()) {
                return User(Id(keys.getInt(1)), name, email, Token(token.toString()), password)
            }

            throw SQLException("Error while creating a new user.")
        }

    override fun getUserById(userId: Id): User? =
        dataSource.connection.use {
            val sql = "SELECT * FROM users WHERE uid = ?"
            val stmt =
                it.prepareStatement(
                    sql
                ).apply {
                    setInt(1, userId.id)
                }

            val rs = stmt.executeQuery()

            if (rs.next()) {
                return rs.toUser()
            }
            return null
        }

    override fun getUserByToken(token: Token): User? =
        dataSource.connection.use {
            val sql = "SELECT * FROM users WHERE users.token = ?"
            val stmt =
                it.prepareStatement(
                    sql
                ).apply {
                    setString(1, token.token)
                }

            val rs = stmt.executeQuery()

            if (rs.next()) {
                return rs.toUser()
            }
            return null
        }


    override fun getAllUsers(): List<User> =
        dataSource.connection.use {
            val users = mutableListOf<User>()
            val sql = "SELECT uid, name, email, token, password FROM users"
            val stmt = it.prepareStatement(sql)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                users.add(
                    rs.toUser()
                )
            }
            return users
        }
}