package pt.isel.ls.storage

import pt.isel.ls.domain.User

interface IStorage{

    fun getUsers(): List<User>
}