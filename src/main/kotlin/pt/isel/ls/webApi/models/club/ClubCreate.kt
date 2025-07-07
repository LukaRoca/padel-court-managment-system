package pt.isel.ls.webApi.models.club

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.User
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.generateRandomString
import pt.isel.ls.webApi.dto.UserDetails

@Serializable
data class ClubCreate(
    val name: Name,
    val owner: UserDetails
) {
    companion object Factory {
        fun create(
            name: Name = Name(generateRandomString()),
            owner: UserDetails
        ): ClubCreate {
            return ClubCreate(name, owner)
        }
    }
}