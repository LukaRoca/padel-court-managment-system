package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.UserDataMem

object UserServices {
    fun getUserById(userId: Id): User? {
        return UserDataMem.getUserById(userId)
    }

    fun createUser(name: Name, email: Email): User {
        return UserDataMem.createUser(name, email)
    }

    fun getUserByToken(token: Token): User? {
        return UserDataMem.getUserByToken(token)
    }


}

