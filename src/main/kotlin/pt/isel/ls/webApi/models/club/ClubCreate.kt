package pt.isel.ls.webApi.models.club

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.User
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.generateRandomString

@Serializable
data class ClubCreate(val name: String) {
    companion object Factory {
        fun create(
            name: String = generateRandomString(),
        ): ClubCreate {
            return ClubCreate(name)
        }
    }
}