package pt.isel.ls.webApi.models.club

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Club

@Serializable
data class ClubResponse private constructor(
    val id: Int,
    val name: String
) {
    companion object {
        operator fun invoke(club: Club): ClubResponse {
            return ClubResponse(
                id = club.id.id,
                name = club.name.name
            )
        }
    }
}