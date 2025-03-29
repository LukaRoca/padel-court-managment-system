package pt.isel.ls.storage.iStorage

import pt.isel.ls.domain.Court
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name

interface CourtIStorage {

    fun getCourtByClubId(id: Id): List<Court>?
    fun createCourt(name: Name, cid: Id): Court?
    fun getCourtById(id : Id) : Court?
}