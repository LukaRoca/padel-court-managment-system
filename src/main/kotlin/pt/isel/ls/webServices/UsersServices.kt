package pt.isel.ls.webServices

import pt.isel.ls.domain.User
import pt.isel.ls.storage.DataMem

object UserServices {

    fun getUserById(userId: Int): User? {
        return DataMem.getUserById(userId)
    }

    fun createUser(name: String, email: String): Pair<Int, String> {
        return DataMem.createUser(name,email)
    }

    fun getUserByToken(token: String): User? {
        return DataMem.getUserByToken(token)
    }


}