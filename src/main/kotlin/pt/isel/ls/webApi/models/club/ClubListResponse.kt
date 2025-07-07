package pt.isel.ls.webApi.models.club

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.PaginatedResponse

@Serializable
class ClubListResponse private constructor(
    val clubs: List<ClubResponse>,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val total: Int
) {
    companion object {
        operator fun invoke(clubsResponse : PaginatedResponse<ClubResponse>): ClubListResponse {
            val (clubs, hasNext, hasPrevious) = clubsResponse
            return ClubListResponse(
                clubs = clubs,
                hasNext = hasNext,
                hasPrevious = hasPrevious,
                total = clubs.size
            )
        }
    }
}