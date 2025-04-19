package pt.isel.ls.storage.iStorage

import pt.isel.ls.domain.*

interface CourtIStorage {

    fun getCourtByClubId(id: Id): List<Court>?
    fun createCourt(name: Name, club: Club): Court?
    fun getCourtById(id : Id) : Court?
}