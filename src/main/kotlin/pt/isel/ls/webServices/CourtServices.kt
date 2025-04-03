package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.CourtIStorage

open class CourtServices (private val db: CourtIStorage) {

    fun createCourt(name : Name, id : Id, token: Token) : Court? {
        return db.createCourt(name, id, token)
    }
    fun getCourtById(id : Id ) : Court? {
        return db.getCourtById(id)
    }
    fun getCourtsByClub(id : Id) : List<Court>? {
        return db.getCourtByClubId(id)
    }


}

