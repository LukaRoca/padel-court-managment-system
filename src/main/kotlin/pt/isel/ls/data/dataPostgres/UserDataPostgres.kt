package pt.isel.ls.data.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.data.UserData
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.PaginatedResponse
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token
import pt.isel.ls.utils.postgres.toUser
import pt.isel.ls.utils.postgres.useWithRollback
import pt.isel.ls.webApi.models.user.UserCreate
import pt.isel.ls.webApi.models.user.UserListElement
import pt.isel.ls.webApi.models.user.UserSearch
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.*
import javax.sql.DataSource

class UserDataPostgres (private val conn: () -> Connection) : UserData {
    override fun createUser(userCreate: UserCreate): User =
        conn().useWithRollback {
            val (name,email,password) = userCreate
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
                return User(Id(keys.getInt(1)), name , email, token, password)
            }

            throw SQLException("Error while creating a new user.")
        }

    override fun getUserById(userId: Id): User? = fetchUser("uid", userId.id)

    override fun getUserByToken(token: UUID): User? = fetchUser("token", token)

    override fun getUserByEmail(email: Email): User? = fetchUser("email", email.value)

    override fun getUserByName(name : Name): User? = fetchUser("name", name.name)


    override fun getAllUsers(searchParameters: UserSearch, skip: Int, limit: Int): PaginatedResponse<UserListElement> =
        conn().useWithRollback {
            val userName = searchParameters.username
            val sql = "SELECT * FROM users ${if (userName.isNullOrBlank()) "" else "WHERE name = ?"}"
            val stmt = it.prepareStatement(sql).apply {
                userName?.let { setString(1, "$userName%") }
            }

            val rs = stmt.executeQuery()
            val users = mutableListOf<UserListElement>()

            while (rs.next()) {
                users.add(UserListElement(rs.toUser()))
            }
            return PaginatedResponse.fromList(users, skip, limit)
        }

    private fun fetchUser(identifier : String, value: Any): User? =
        conn().useWithRollback {
            val query = "SELECT * FROM users WHERE $identifier = ?"

            val stmt = it.prepareStatement(query).apply {
                setObject(1, value)
            }

            val rs = stmt.executeQuery()

            if (rs.next()) {
                return rs.toUser()
            }
            return null
        }
}