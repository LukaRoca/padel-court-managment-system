package pt.isel.ls.data.data

import pt.isel.ls.domain.*
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name

interface CourtData {
    fun getCourtByClubId(id: Id): List<Court>?

    fun createCourt(name: Name, club: Club): Court?

    fun getCourtById(id : Id) : Court?
}