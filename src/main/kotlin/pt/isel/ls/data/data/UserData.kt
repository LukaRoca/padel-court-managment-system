package pt.isel.ls.data.data

import pt.isel.ls.domain.*
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token

interface UserData {
    fun createUser(name: Name, email: Email, password: Password) : User
    fun getUserById(userId: Id): User?
    fun getUserByToken(token: Token): User?
    fun getAllUsers(): List<User>
}