package pt.isel.ls.storage.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.ClubIStorage
import pt.isel.ls.storage.dataMem.UserDataMem.getUserByToken

object ClubDataMem : ClubIStorage {

    val club = mutableListOf(
        Club(Id(1), Name("Padel N"), Owner(User(
            Id(1), Name("Michael Jackson"), Email("michael@gmail.com"),Token("42449fc7-0006-458d-b4dc-324d5583f634"))))
    )

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


}

