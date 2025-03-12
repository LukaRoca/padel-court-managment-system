package pt.isel.ls.webServices

import pt.isel.ls.domain.User
import pt.isel.ls.storage.DataMem

class userService(private val storage : DataMem) {
    fun getUsers(): List<User> {
        return storage.getUsers()
    }

}
