package pt.isel.ls.webApi.routes.user

import pt.isel.ls.domain.User
import pt.isel.ls.storage.DataMem

object UserServices {

    fun createUser(name: String, email: String) : User {
        return DataMem.createUser(name, email)
    }
    fun getUserById(userId: Int): User? {
        return DataMem.getUserById(userId)
    }
    fun getUsers(): List<User> {
        return DataMem.getUsers()
    }

}