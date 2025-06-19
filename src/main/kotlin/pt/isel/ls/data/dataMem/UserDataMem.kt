package pt.isel.ls.data.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.data.data.UserData
import pt.isel.ls.utlis.Email
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.Password
import pt.isel.ls.utlis.Token
import java.util.*

class UserDataMem(private val users: DataMemMap<User> = DataMemMap()) : UserData {

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