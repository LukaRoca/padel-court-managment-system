package pt.isel.ls.data.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.data.ClubData
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Owner
import pt.isel.ls.utils.postgres.toClub
import pt.isel.ls.utils.postgres.toUser
import pt.isel.ls.utils.postgres.useWithRollback
import pt.isel.ls.webApi.models.club.ClubCreate
import java.sql.Connection

import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

class ClubDataPostgres (private val conn: () -> Connection): ClubData {
    override fun createClub(
        clubCreate: ClubCreate,
        uid : Id
    ): Club =
        conn().useWithRollback {
            val name = clubCreate
            val sql = "INSERT INTO club(name, owner) VALUES (?, ?)"
            val stmt = it.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS
            ).apply {
                setString(1, name.name)
                setInt(2, uid.id)
            }

            if (stmt.executeUpdate() == 0) {
                throw SQLException("Error while creating a new club.")
            }

            val key = stmt.generatedKeys

            if (key.next()) {
                return Club(Id(key.getInt(1)), Name(name.name), Id(key.getInt("owner")), mutableListOf())
            }

            throw SQLException("Error while creating a new club.")
        }

    override fun getClubById(cid: Id): Club? = fetchClub("cid", cid.id)

    override fun getClubByName(name: Name): Club? = fetchClub("name", name.name)

    override fun getClubs(): List<Club> =
        conn().useWithRollback {
            val clubs = mutableListOf<Club>()
            val sql = "SELECT * FROM club"
            val stmt = it.prepareStatement(sql)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                clubs.add(
                    rs.toClub()
                )
            }
            return clubs
        }

    override fun deleteClub(club: Club): Boolean =
        conn().useWithRollback {
            val query = "DELETE FROM club WHERE cid = ?"
            val stmt = it.prepareStatement(query).apply {
                setInt(1, club.id.id)
            }
            val rs = stmt.executeUpdate()
            return rs > 0
        }

    private fun fetchClub(identifier : String, value: Any) : Club? =
        conn().useWithRollback {
            val query = "SELECT * FROM club WHERE $identifier = ?"

            val stmt = it.prepareStatement(query).apply {
                setObject(1, value)
            }

            val rs = stmt.executeQuery()

            if (rs.next()) {
                return rs.toClub()
            }
            return null
        }

}