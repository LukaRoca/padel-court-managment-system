package pt.isel.ls.webServices

import pt.isel.ls.checkIfTokenInDb
import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.Token
import pt.isel.ls.storage.iStorage.ClubIStorage
import pt.isel.ls.storage.iStorage.UserIStorage

class ClubServices (private val db : ClubIStorage, private val userDb : UserIStorage) {

    fun createClub(name : Name, token : Token) : Club? {
        checkIfTokenInDb(token, db = userDb)
        val existingClubs = db.getClubs()
        if (existingClubs.any { it.name.name == name.name }) {
            throw IllegalArgumentException("A club with the same name already exists")
        }
        return db.createClub(name, token)
    }

    fun getClubById(clubId: Id): Club? {
        return db.getClubById(clubId)
    }

    fun getClubs(): List<Club> {
        return db.getClubs()
    }
}