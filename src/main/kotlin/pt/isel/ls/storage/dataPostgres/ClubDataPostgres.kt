package pt.isel.ls.storage.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.ClubIStorage

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class ClubDataPostgres (private val connection: Connection): ClubIStorage {
    private val userData = UserDataPostgres(connection)
    override fun getClubById(cid: Id): Club? {
        val sql = "SELECT * FROM club WHERE cid = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, cid.id)
            stmt.executeQuery().use { result ->
                if (result.next()) {
                    val owner = userData.getUserById(Id(result.getInt("owner"))) ?: return null
                    return Club(
                        Id(result.getInt("cid")),
                        Name(result.getString("name")),
                        Owner(owner)
                    )
                }
            }
        }
        return null
    }

    override fun createClub(name: Name, token: Token) : Club? {
        val user = userData.getUserByToken(token) ?: return throw IllegalArgumentException("No user with token $token")
        val sql = "INSERT INTO club(name, owner) VALUES (?, ?)"
        val statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).apply {
            setString(1, name.name)
            setInt(2, user.uid.id)
        }
        if (statement.executeUpdate() == 0) {
            throw SQLException("Error while creating a new club.")
        }
        val key = statement.generatedKeys
        key.next()
        return Club(Id(key.getInt("cid")), name, Owner(user))
    }

    override fun getClubs(): List<Club> {
        val sql = "SELECT * FROM club"
        val clubs = mutableListOf<Club>()
        connection.prepareStatement(sql).use { stmt ->
            stmt.executeQuery().use { result ->
                while (result.next()) {
                    val owner = userData.getUserById(Id(result.getInt("owner"))) ?: return emptyList()
                    clubs.add(Club(Id(result.getInt("cid")), Name(result.getString("name")), Owner(owner)))
                }
            }
        }
        return clubs
    }
}