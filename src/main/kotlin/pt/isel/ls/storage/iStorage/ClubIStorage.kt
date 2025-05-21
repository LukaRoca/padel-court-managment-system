package pt.isel.ls.storage.iStorage

import pt.isel.ls.domain.*

interface ClubIStorage {
    fun createClub(name: Name,  user: User) : Club?
    fun getClubById(cid: Id): Club?
    fun getClubs(): List<Club>
    fun getClubByName(name: Name) : Club?
    fun deleteClub(club: Club): Boolean
}