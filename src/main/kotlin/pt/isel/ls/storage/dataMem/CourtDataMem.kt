package pt.isel.ls.storage.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.storage.dataMem.UserDataMem.getUserByToken
import pt.isel.ls.storage.iStorage.CourtIStorage

object CourtDataMem : CourtIStorage {
    private val courts = mutableListOf(
        Court(Id(1), Name("Padel Court 1"), Club(Id(1), Name("Padel N"), Owner(User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com"), Token("42449fc7-0006-458d-b4dc-324d5583f634"))))))

    private var crid = 2

    override fun getCourtByClubId(id: Id): List<Court>? {
        return courts.filter { it.club.id == id }
    }
    override fun createCourt(name: Name,club: Club): Court? {
            val newCourt = Court(Id(crid), name, club)
            crid++
            courts.add(newCourt)
            return newCourt
    }
    override fun getCourtById(id : Id) : Court? {
        return courts.find { it.id == id }
    }
}

