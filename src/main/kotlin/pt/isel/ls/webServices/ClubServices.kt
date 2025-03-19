package pt.isel.ls.webServices

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.Token
import pt.isel.ls.storage.ClubDataMem
import pt.isel.ls.storage.ClubIStorage
import pt.isel.ls.storage.UserDataMem

class ClubServices (private val db : ClubIStorage) {

    fun createClub(name : Name, token : Token) : Club? {
        return db.createClub(name, token)
    }

    fun getClubById(clubId: Id): Club? {
        return db.getClubById(clubId)
    }

    fun getClubs(): List<Club> {
        return db.getClubs()
    }
}