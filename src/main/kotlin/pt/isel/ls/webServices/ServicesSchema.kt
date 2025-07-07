package pt.isel.ls.webServices

import pt.isel.ls.data.Data
import pt.isel.ls.domain.User
import pt.isel.ls.utils.exceptions.AuthorizationException
import java.util.UUID
import kotlin.collections.get

abstract class ServicesSchema(protected val data: Data) {
    fun <T> withAuthorization(token: UUID, action: (User) -> T): T {
        val user = bearerToken(token)
        return action(user)
    }

    protected fun bearerToken(token: UUID): User {
        return data.user.getUserByToken(token)
            ?: throw AuthorizationException("Missing or invalid bearer token")
    }
}