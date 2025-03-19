package pt.isel.ls.webServices

import pt.isel.ls.domain.Email
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.User
import pt.isel.ls.storage.DataMem

object UserServices {
    fun getUserById(userId: Id): User? {
        return DataMem.getUserById(userId)
    }

    fun createUser(name: Name, email: Email): Pair<Int, String> {
        return DataMem.createUser(name,email)
    }

    fun getUserByToken(token: String): User? {
        return DataMem.getUserByToken(token)
    }


}