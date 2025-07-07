package pt.isel.ls.webApi.models.court

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Court

@Serializable
class CourtResponse private constructor(
    val name: String,
    val club: Int
){
    companion object {
        operator fun invoke(court: Court): CourtResponse {
            return CourtResponse(court.name.name, court.club.id)
        }
    }
}