package pt.isel.ls.data.data

import pt.isel.ls.domain.*
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name

interface ClubData {
    fun createClub(name: Name, user: User) : Club?

    fun getClubById(cid: Id): Club?

    fun getClubs(): List<Club>

    fun getClubByName(name: Name) : Club?

    fun deleteClub(club: Club): Boolean
}