package pt.isel.ls.webServices

import pt.isel.ls.domain.Club
import pt.isel.ls.storage.DataMem

object ClubServices {
    fun createClub(name : String, token : String) : Club? {
        val user = DataMem.getUserByToken(token) ?: return null
        return DataMem.createClub(name, user)
    }
    fun getClubById(id: Int): Club? {
        TODO()
    }



    fun getClubById(clubId: Int): Club? {
        return DataMem.getClubById(clubId)
    }

    fun getClubs(): List<Club> {
        return DataMem.getClubs()
    }
}