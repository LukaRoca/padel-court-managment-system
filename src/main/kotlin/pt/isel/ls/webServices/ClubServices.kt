package pt.isel.ls.webServices

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.Token
import pt.isel.ls.storage.dataPostgres.UserDataPostgres
import pt.isel.ls.storage.iStorage.ClubIStorage
import pt.isel.ls.storage.iStorage.IStorage

class ClubServices (private val db : IStorage) {
    fun createClub(name : Name, token : Token) : Club? {
        val user = db.user.getUserByToken(token) ?: throw IllegalArgumentException("Invalid token")
        return db.club.createClub(name, user)
    }

    fun getClubById(clubId: Id): Club? {
        return db.club.getClubById(clubId)
    }

    fun getClubs(): List<Club> {
        return db.club.getClubs()
    }
}