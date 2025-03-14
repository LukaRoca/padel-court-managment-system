package pt.isel.ls.storage

import pt.isel.ls.domain.Email
import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Owner


object ClubDataMem  {
    private val clubs = mutableListOf(
        Club(Id(1), Name("Michael Jackson"), Owner(Name("Sporting"))),
    )

    private var cid = 1
    /*
    fun createClub(name: String): Club {
        val newClub = Club
    }

     */
}