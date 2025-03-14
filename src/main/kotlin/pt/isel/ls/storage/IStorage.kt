package pt.isel.ls.storage

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.User

interface IStorage{
    /*
    fun getUsers(): List<User>
    fun getUserById(userId: Int): User?
    fun getClubs() : List<Club>
    fun getClubById(clubId: Int) : Club?

     */

    fun createUser(name: String, email: String) : User
    fun getUserById(userId: Int): User?
}