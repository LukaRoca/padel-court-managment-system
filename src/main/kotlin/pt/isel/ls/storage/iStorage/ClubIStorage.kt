package pt.isel.ls.storage.iStorage

import pt.isel.ls.domain.*

interface ClubIStorage {
    fun createClub(name: Name,  token: Token) : Club?
    fun getClubById(cid: Id): Club?
    fun getClubs(): List<Club>
}