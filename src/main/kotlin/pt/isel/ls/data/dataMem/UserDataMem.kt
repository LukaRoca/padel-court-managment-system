package pt.isel.ls.data.dataMem

import pt.isel.ls.data.UserData
import pt.isel.ls.domain.*
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import java.util.UUID
import pt.isel.ls.webApi.models.user.UserCreate

class UserDataMem(private val users: DataMemMap<User> = DataMemMap()) : UserData {

    override fun createUser(userCreate: UserCreate): User {
        val token = UUID.randomUUID()
        val newUser = User(
            Id(users.nextId.get()),
            userCreate.name,
            userCreate.email,
            token,
            userCreate.password
        )
        users.map[users.nextId.get()] = newUser
        return newUser
    }

    override fun getUserById(userId: Id): User? = users.map[userId.id]

    override fun getUserByEmail(email: Email): User? =
        users.map.values.find { it.email == email }

    override fun getUserByName(name: Name): User? =
        users.map.values.find { it.name == name }

    override fun getUserByToken(token: UUID): User? =
        users.map.values.find { UUID.fromString(it.token.toString()) == token }

    override fun getAllUsers(): List<User> = users.map.values.toList()
}