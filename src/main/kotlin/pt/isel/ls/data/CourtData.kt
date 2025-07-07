package pt.isel.ls.data

import pt.isel.ls.domain.*
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.webApi.models.court.CourtCreate

interface CourtData {
    fun getCourtByClubId(id: Id): List<Court>?

    fun createCourt(courtCreate: CourtCreate, club: Id): Court

    fun getCourtById(id : Id) : Court?
}