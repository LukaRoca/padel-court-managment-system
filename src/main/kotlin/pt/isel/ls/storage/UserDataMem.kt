package pt.isel.ls.storage

import pt.isel.ls.domain.*
import java.util.*

object UserDataMem : UserIStorage {

    private val users = mutableListOf(
        User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com"), Token("42449fc7-0006-458d-b4dc-324d5583f634"))
    )

    private var uid = 2

    override fun createUser(name: Name, email: Email) : User {
        val token = UUID.randomUUID().toString()
        val newUser = User(Id(uid), name, email, Token(token))
        uid++
        users.add(newUser)
        return newUser
    }

    override fun getUserById(userId: Id): User? {
        return users.find { it.uid == userId }
    }

    override fun getUserByToken(token: Token): User? {
        return users.find { it.token == token }
    }

}