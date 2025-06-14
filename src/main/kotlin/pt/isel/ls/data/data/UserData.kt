package pt.isel.ls.data.data

import pt.isel.ls.domain.*
import pt.isel.ls.utlis.Email
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.Password
import pt.isel.ls.utlis.Token

interface UserData {
    fun createUser(name: Name, email: Email, password: Password) : User
    fun getUserById(userId: Id): User?
    fun getUserByToken(token: Token): User?
    fun getAllUsers(): List<User>
}