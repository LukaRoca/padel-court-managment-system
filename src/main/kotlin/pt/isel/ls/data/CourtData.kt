package pt.isel.ls.data

import pt.isel.ls.domain.*
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.PaginatedResponse
import pt.isel.ls.webApi.models.club.ClubResponse
import pt.isel.ls.webApi.models.court.CourtCreate
import pt.isel.ls.webApi.models.court.CourtResponse

interface CourtData {
    fun getCourtByClubId(
        id: Id,
        limit: Int,
        skip: Int
    ): PaginatedResponse<CourtResponse>

    fun createCourt(courtCreate: CourtCreate, club: Id): Court

    fun getCourtByName(name: Name): Court?

    fun getCourtById(id : Id) : Court?
}