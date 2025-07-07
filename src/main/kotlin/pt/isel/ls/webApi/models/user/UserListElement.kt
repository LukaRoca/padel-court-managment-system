package pt.isel.ls.webApi.models.user

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.User

@Serializable
class UserListElement private constructor(
    val id: Int,
    val name: String,
) {
    companion object {
        operator fun invoke(user: User): UserListElement {
            return UserListElement(user.uid.id, user.name.name)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserListElement

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        return result
    }
}