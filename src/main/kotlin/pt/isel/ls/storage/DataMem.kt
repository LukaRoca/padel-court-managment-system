package pt.isel.ls.storage

import pt.isel.ls.domain.*

object DataMem : IStorage {
    private val users = mutableMapOf<Id, User>()

    override fun getUsers(): List<User> = users.values.toList()


}