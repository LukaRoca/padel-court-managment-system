package pt.isel.ls.storage.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.ClubIStorage
import pt.isel.ls.storage.dataMem.UserDataMem.getUserByToken

object ClubDataMem : ClubIStorage {

    val club = mutableListOf<Club>()
    private var cid = 2

    override fun getClubById(cid: Id): Club? {
        return club.find { it.id == cid }
    }

    override fun createClub(name: Name, user: User): Club? {
        val user = getUserByToken(user.token) ?: return null
        val newClub = Club(Id(cid), name, Owner(user))
        cid++
        club.add(newClub)
        return newClub
    }

    override fun getClubs(): List<Club> {
        return club
    }

    override fun getClubByName(name: Name): Club? {
        return club.find { it.name == name }
    }

    override fun deleteClub(club: Club): Boolean {
        TODO()
    }


}

