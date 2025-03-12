package pt.isel.ls.storage

import pt.isel.ls.domain.User

object DataMem : IStorage {
    private val users = mutableListOf<User>()
    private val clubs = mutableListOf<Club>()
    private val rentals = mutableListOf<Rental>()

    override fun getUsers(): List<User> = users.toList()

}