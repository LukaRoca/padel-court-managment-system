package pt.isel.ls.data.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.data.UserData
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token
import java.util.*

class UserDataMem(private val users: DataMemMap<User> = DataMemMap()) : pt.isel.ls.data.UserData {

    override fun createUser(
        name: Name, email: Email, password: Password
    ): User {
        val token = UUID.randomUUID().toString()
        val newUser = User(Id(users.nextId.get()), name, email, Token(token), password)
        users.map[users.nextId.get()] = newUser
        return newUser
    }

    override fun getUserById(userId: Id): User? = users.map[userId.id]

    override fun getUserByToken(token: Token): User? = users.map.values.find { it.token == token }

    override fun getAllUsers(): List<User> = users.map.values.toList()
}