package pt.isel.ls.webServices

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.storage.DataMem

object ClubServices {
    fun createClub(name : Name, token : String) : Club? {
        val user = DataMem.getUserByToken(token) ?: return null
        return DataMem.createClub(name, user)
    }

    fun getClubById(clubId: Id): Club? {
        return DataMem.getClubById(clubId)
    }

    fun getClubs(): List<Club> {
        return DataMem.getClubs()
    }
}