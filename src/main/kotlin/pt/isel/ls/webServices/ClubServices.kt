package pt.isel.ls.webServices

import pt.isel.ls.utils.PaginatedResult
import pt.isel.ls.domain.Club
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Token
import pt.isel.ls.utils.paginateWithInfo
import pt.isel.ls.data.Data
import pt.isel.ls.webApi.dto.ClubDetails
import pt.isel.ls.webApi.dto.UserDetails

class ClubServices (private val db : pt.isel.ls.data.Data) {
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

    fun deleteClub(clubId: Id, token: Token): Boolean {

        val club = db.club.getClubById(clubId) ?: throw IllegalStateException("Club not found with this id $clubId")

        if( club.owner.user.token != token) {
            throw IllegalStateException("You are not the owner of this club")
        }

        return db.club.deleteClub(club)
    }
}