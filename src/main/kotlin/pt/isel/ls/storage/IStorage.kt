package pt.isel.ls.storage

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.User

interface IStorage{
    fun createUser(name: String, email: String) : Pair<Int, String>
    fun getUserById(userId: Int): User?
    fun getUserByToken(token: String): User?
    fun createClub(name: String, user: User) : Club
    fun getClubById(cid: Int): Club?
    fun getClubs(): List<Club>
}