package pt.isel.ls.webApi.models.club

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.User
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.generateRandomString

@Serializable
data class ClubCreate(val name: Name) {
    companion object Factory {
        fun create(
            name: Name = Name(generateRandomString()),
        ): ClubCreate {
            return ClubCreate(name)
        }
    }
}