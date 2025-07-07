package pt.isel.ls.data.dataMem

import pt.isel.ls.data.UserData
import pt.isel.ls.domain.*
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Token
import pt.isel.ls.webApi.models.user.UserCreate
import java.util.*

class UserDataMem(private val users: DataMemMap<User> = DataMemMap()) : UserData {

    override fun createUser(userCreate: UserCreate): User {
        val token = UUID.randomUUID().toString()
        val newUser = User(
            Id(users.nextId.get()),
            userCreate.name,
            userCreate.email,
            Token(token),
            userCreate.password
        )
        users.map[users.nextId.get()] = newUser
        return newUser
    }

    override fun getUserById(userId: Id): User? = users.map[userId.id]

    override fun getUserByToken(token: Token): User? = users.map.values.find { it.token == token }

    override fun getAllUsers(): List<User> = users.map.values.toList()
}