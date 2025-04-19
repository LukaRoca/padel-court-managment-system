package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.CourtIStorage
import pt.isel.ls.storage.iStorage.IStorage

open class CourtServices (private val db: IStorage) {

    fun createCourt(name : Name, cid : Id, token: Token) : Court? {
        val user = db.user.getUserByToken(token) ?: throw NullPointerException("User with token ${token} not found")
        val club = db.club.getClubById(cid) ?: throw NullPointerException("Club with id ${cid} not found")
        if (club.owner.user != user) throw IllegalArgumentException("Id or token not valid")
        return db.court.createCourt(name, club)
    }
    fun getCourtById(crid: Id ) : Court? {
        return db.court.getCourtById(crid)
    }
    fun getCourtsByClubId(cid : Id) : List<Court>? {
        return db.court.getCourtByClubId(cid)
    }


}

