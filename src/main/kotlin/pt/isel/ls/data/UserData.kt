package pt.isel.ls.data

import pt.isel.ls.domain.*
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.PaginatedResponse
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token
import pt.isel.ls.webApi.models.user.UserCreate
import pt.isel.ls.webApi.models.user.UserListElement
import pt.isel.ls.webApi.models.user.UserSearch
import java.util.UUID

interface UserData {
    fun createUser(userCreate: UserCreate) : User
    fun getUserById(userId: Id): User?
    fun getUserByEmail(email: Email): User?
    fun getUserByName(name : Name) : User?
    fun getUserByToken(token: UUID): User?
    fun getAllUsers(searchParameters: UserSearch, skip: Int, limit: Int): PaginatedResponse<UserListElement>
}