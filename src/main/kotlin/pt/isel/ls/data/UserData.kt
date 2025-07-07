package pt.isel.ls.data

import pt.isel.ls.domain.*
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token
import pt.isel.ls.webApi.models.user.UserCreate

interface UserData {
    fun createUser(userCreate: UserCreate) : User
    fun getUserById(userId: Id): User?
    fun getUserByToken(token: Token): User?
    fun getAllUsers(): List<User>
}