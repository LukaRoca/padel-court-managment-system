package pt.isel.ls.webServices

import pt.isel.ls.PaginatedResult
import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.Token
import pt.isel.ls.paginateWithInfo
import pt.isel.ls.storage.dataPostgres.UserDataPostgres
import pt.isel.ls.storage.iStorage.ClubIStorage
import pt.isel.ls.storage.iStorage.IStorage
import pt.isel.ls.webApi.dto.ClubDetails
import pt.isel.ls.webApi.dto.UserDetails

class ClubServices (private val db : IStorage) {
    fun createClub(name : Name, token : Token) : Club? {
        val user = db.user.getUserByToken(token) ?: throw IllegalArgumentException("Invalid token")
        val existingClubs = db.club.getClubs()
        for (club in existingClubs) {
            if (club.name.name == name.name) {
                throw IllegalArgumentException("Already exists one club with the same name: '${name.name}'")
            }
        }
        return db.club.createClub(name, user)
    }

    fun getClubById(clubId: Id): Club? {
        return db.club.getClubById(clubId)
    }

    fun getClubs(limit : Int, skip : Int): PaginatedResult<ClubDetails> {
        val clubs = db.club.getClubs().map {
            ClubDetails(
                it.id.id,
                it.name.name,
                UserDetails(
                    it.owner.user.uid.id,
                    it.owner.user.name.name,
                    it.owner.user.email.value,
                    it.owner.user.token.token
                )
            )
        }
        return clubs.paginateWithInfo(limit, skip)
    }

    fun getClubByName(name: Name): Club? {
        return db.club.getClubByName(name)
    }

    fun deleteClub(clubId: Id): Boolean {
       val club = db.club.getClubById(clubId) ?: throw IllegalStateException("Club not found with this id $clubId")
        return db.club.deleteClub(club)
    }
}