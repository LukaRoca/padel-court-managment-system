package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.data.Data
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token
import pt.isel.ls.utils.exceptions.BadRequestException
import pt.isel.ls.webApi.models.user.UserCreate
import pt.isel.ls.webApi.models.user.UserDetails
import pt.isel.ls.webApi.models.user.UserListResponse
import pt.isel.ls.webApi.models.user.UserLogin
import pt.isel.ls.webApi.models.user.UserResponse
import pt.isel.ls.webApi.models.user.UserSearch
import java.util.UUID

class UserServices (private val db : Data) : ServicesSchema(db) {
    fun createUser(userCreate: UserCreate): UserResponse {
        if (db.user.getUserByName(userCreate.name) != null) {
            throw BadRequestException("The given name is already taken")
        }
        if (db.user.getUserByEmail(userCreate.email) != null) {
            throw BadRequestException("The given email is already taken")
        }

        val user = db.user.createUser(userCreate)
        return UserResponse(user)
    }

    fun getUser(
        userId : Id,
        token : UUID
    ): UserDetails = withAuthorization(token) {
        val user = db.user.getUserById(userId)
            ?: throw NoSuchElementException("No user with id $userId was found")
        return@withAuthorization UserDetails(user)
    }

    fun getAllUsers(
        searchParameters: UserSearch,
        token: UUID,
        limit : Int,
        skip : Int
    ): UserListResponse = withAuthorization(token) {
        val users = db.user.getAllUsers(searchParameters,skip,limit)
        return@withAuthorization UserListResponse(users)
    }
    fun loginUser(userLogin: UserLogin): UserResponse {
        val user = db.user.getUserByEmail(userLogin.email)
            ?: throw NoSuchElementException("No user found with email: ${userLogin.email}")
        if (!Password(userLogin.password).verify(user.password.value)) {
            throw IllegalArgumentException("The given password is incorrect")
        }
        return UserResponse(user)
    }

}