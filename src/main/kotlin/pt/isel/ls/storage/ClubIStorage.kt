package pt.isel.ls.storage

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.User

interface ClubIStorage {
    fun createClub(name: Name, user: User) : Club
    fun getClubById(cid: Id): Club?
    fun getClubs(): List<Club>
}