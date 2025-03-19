package pt.isel.ls.webServices

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.Token
import pt.isel.ls.storage.ClubDataMem
import pt.isel.ls.storage.UserDataMem

object ClubServices {

    fun createClub(name : Name, token : Token) : Club? {
        val user = UserDataMem.getUserByToken(token) ?: return null
        return ClubDataMem.createClub(name, user)
    }

    fun getClubById(clubId: Id): Club? {
        return ClubDataMem.getClubById(clubId)
    }

    fun getClubs(): List<Club> {
        return ClubDataMem.getClubs()
    }
}