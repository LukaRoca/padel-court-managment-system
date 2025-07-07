package pt.isel.ls.webApi.models.user

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.User
import pt.isel.ls.utils.Email

@Serializable
class UserLogin private constructor(
    val email: Email,
    val password : String
) {
    companion object {
        operator fun invoke(user : User) : UserLogin {
            return UserLogin(user.email, user.password.value)
        }
    }
}