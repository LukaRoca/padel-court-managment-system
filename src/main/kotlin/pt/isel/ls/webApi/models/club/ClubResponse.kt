package pt.isel.ls.webApi.models.club

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Club

@Serializable
class ClubResponse private constructor(
    val name : String
) {
    companion object {
        operator fun invoke(club: Club) : ClubResponse {
            return ClubResponse(club.name.name)
        }
    }
}