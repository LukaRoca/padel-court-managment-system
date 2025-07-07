package pt.isel.ls.webApi.models.user

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.*

@Serializable
data class UserCreate (val name: Name, val email: Email, val password: Password) {
    companion object Factory {
        fun create(
            name : Name = Name(generateRandomString()),
            email: Email = generateRandomEmail(),
            password: Password = generateRandomPassword()
        ) : UserCreate {
            return UserCreate(name, email, password)
        }
    }
}