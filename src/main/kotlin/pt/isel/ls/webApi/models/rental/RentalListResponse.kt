package pt.isel.ls.webApi.models.rental

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.PaginatedResponse
import pt.isel.ls.webApi.models.court.CourtResponse

@Serializable
class RentalListResponse private constructor(
    val rentals: List<RentalResponse>,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val total: Int
){
    companion object {
        operator fun invoke(rentalsResponse : PaginatedResponse<RentalResponse>): RentalListResponse {
            val (rentals, hasNext, hasPrevious) = rentalsResponse
            return RentalListResponse(
                rentals = rentals,
                hasNext = hasNext,
                hasPrevious = hasPrevious,
                total = rentals.size
            )
        }
    }
}