package pt.isel.ls.storage

import pt.isel.ls.domain.*

interface IStorage{
    fun createUser(name: Name, email: Email) : Pair<Int, String>
    fun getUserById(userId: Id): User?
    fun getUserByToken(token: String): User?
    fun createClub(name: Name, user: User) : Club
    fun getClubById(cid: Id): Club?
    fun getClubs(): List<Club>
    fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: String): Rental?
    fun getCourtByClub(club: Club): List<Court>
    fun createCourt(name: Name, cid: Id): Court
    fun getCourt(id : Id ) : Court?
    fun getRentalById(rentalId: Id): Rental?
    fun getRentalList(cid: Id, crid: Id, date: Date): List<Rental>?
    fun getRentalsOfUser(cid: Id): List<Rental>?
    fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int>
}