package pt.isel.ls.storage

import pt.isel.ls.domain.*

object ClubDataMem : ClubIStorage{

    private val clubs = mutableListOf(
        Club(Id(1), Name("Padel N"), Owner(User(
            Id(1), Name("Michael Jackson"), Email("michael@gmail.com"),Token("42449fc7-0006-458d-b4dc-324d5583f634"))))
    )

    private var cid = 2

    override fun getClubById(cid: Id): Club? {
        return clubs.find { it.id == cid }
    }

    override fun createClub(name: Name, user: User): Club {
        val newClub = Club(Id(cid), name, Owner(user))
        cid++
        clubs.add(newClub)
        return newClub
    }

    override fun getClubs(): List<Club> {
        return clubs
    }


}