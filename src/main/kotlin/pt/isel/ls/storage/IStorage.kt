package pt.isel.ls.storage

import pt.isel.ls.domain.*

interface IStorage{
    fun createUser(name: String, email: String) : Pair<Int, String>
    fun getUserById(userId: Int): User?
    fun getUserByToken(token: String): User?
    fun createClub(name: String, user: User) : Club
    fun getClubById(cid: Int): Club?
    fun getClubs(): List<Club>
    fun createRental(cid: Int, crid: Int, date: String, duration: Int): Rental?
    fun getCourtByClub(club: Club): List<Court>
    fun createCourt(name: String, cid: Int): Court
    fun getCourt(id : Int ) : Court?
}