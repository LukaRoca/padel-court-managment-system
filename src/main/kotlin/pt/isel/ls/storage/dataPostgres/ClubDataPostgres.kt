package pt.isel.ls.storage.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.ClubIStorage

import java.sql.Connection

class ClubDataPostgres (private val connection: Connection): ClubIStorage {
    private var cid = 1
    override fun getClubById(cid: Id): Club? {
        TODO()
    }

    override fun createClub(name: Name, token: Token): Club? {
        TODO()
    }

    override fun getClubs(): List<Club> {
        TODO()
    }
}