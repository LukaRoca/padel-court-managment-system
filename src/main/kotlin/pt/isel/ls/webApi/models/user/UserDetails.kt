package pt.isel.ls.webApi.models.user

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.User

@Serializable
class UserDetails private constructor(val id: Int, val name: String, val email: String) {

    companion object {
        operator fun invoke(user : User) : UserDetails {
            return UserDetails(user.uid.id, user.name.name, user.email.value)
        }
    }

    override operator fun equals(other : Any?) : Boolean {

        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserDetails

        if (id != other.id) return false
        if (name != other.name) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }
}