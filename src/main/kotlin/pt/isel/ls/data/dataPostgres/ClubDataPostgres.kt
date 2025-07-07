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

val sqlClub = """
    SELECT  club.cid as c_id,
            club.name as c_name,
            club.owner as o_id,
            users.uid,
            users.name,
            users.email,
            users.token,
            users.password
            FROM club
""".trimIndent()

class ClubDataPostgres (private val conn: () -> Connection): ClubData {


    /*
    override fun createClub(
        name: Name, user: User
    ) : Club? =
        dataSource.connection.use {
            val sql = "INSERT INTO club(name, owner) VALUES (?, ?)"
            val stmt = it.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS
            ).apply {
                setString(1,name.name)
                setInt(2, user.uid.id)
            }

            if (stmt.executeUpdate() == 0) {
                throw SQLException("Error while creating a new club.")
            }

            val key = stmt.generatedKeys

            if (key.next()) {
                return Club(Id(key.getInt(1)), name, Owner(user))
            }

            throw SQLException("Error while creating a new club.")
        }


     */

    override fun createClub(
        clubCreate: ClubCreate,
        uid : Id
    ): Club? =
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
                return Club(Id(key.getInt(1)), name.name, )
            }

            throw SQLException("Error while creating a new club.")
        }


    override fun getClubById(cid: Id): Club? =
        dataSource.connection.use {
            val sql = buildString {
                append(sqlClub)
                append(" INNER JOIN users ON club.owner = users.uid ")
                append(" WHERE club.cid = ? ")
            }
            val stmt = it.prepareStatement(sql)
            stmt.setInt(1, cid.id)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                return rs.toClub(rs.toUser())
            }
            return null
        }

    override fun getClubs(): List<Club> =
        dataSource.connection.use {
            val clubs = mutableListOf<Club>()
            val sql = buildString {
                append(sqlClub)
                append(" INNER JOIN users ON club.owner = users.uid ")
            }
            val stmt = it.prepareStatement(sql)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                clubs.add(
                    rs.toClub(rs.toUser())
                )
            }
            return clubs
        }

    override fun getClubByName(name: Name): Club? =
        dataSource.connection.use {
            val sql = buildString {
                append(sqlClub)
                append(" INNER JOIN users ON club.owner = users.uid")
                append(" WHERE club.name = ?")
            }
            val stmt = it.prepareStatement(sql)
            stmt.setString(1, name.name)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                return rs.toClub(rs.toUser())
            }
            return null
        }

    override fun deleteClub(club: Club): Boolean =
        dataSource.connection.use {
            val sql = "DELETE FROM club WHERE club.cid = ?"
            val stmt = it.prepareStatement(sql)
            stmt.setInt(1, club.id.id)
            val rs = stmt.executeUpdate()
            return rs > 0
        }
}