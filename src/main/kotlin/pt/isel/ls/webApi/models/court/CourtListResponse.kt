package pt.isel.ls.webApi.models.court

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Court
import pt.isel.ls.utils.PaginatedResponse
import pt.isel.ls.webApi.models.club.ClubListResponse
import pt.isel.ls.webApi.models.club.ClubResponse

@Serializable
class CourtListResponse private constructor(
    val courts: List<CourtResponse>,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val total: Int
){
    companion object {
        operator fun invoke(courtsResponse : PaginatedResponse<CourtResponse>): CourtListResponse {
            val (courts, hasNext, hasPrevious) = courtsResponse
            return CourtListResponse(
                courts = courts,
                hasNext = hasNext,
                hasPrevious = hasPrevious,
                total = courts.size
            )
        }
    }
}