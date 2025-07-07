package pt.isel.ls.data.dataPostgres
import pt.isel.ls.domain.*
import pt.isel.ls.data.CourtData
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.postgres.toClub
import pt.isel.ls.utils.postgres.toCourt
import pt.isel.ls.utils.postgres.toUser
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

val sqlCourt = """
    SELECT court.crid as cr_rid,
            court.name as cr_name,
            court.club as c_rid,
            club.cid as c_id,
            club.name as c_name,
            club.owner as c_owner,
            users.uid,
            users.token,
            users.name,
            users.email,
            users.password
            FROM court
""".trimIndent()

class CourtDataPostgres (private val conn: () -> Connection) : CourtData {
    override fun createCourt(name: Name, club: Club): Court? =
        dataSource.connection.use {
            val sql = "INSERT INTO court(name, club) VALUES (?, ?)"
            val stmt = it.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS
            ).apply {
                setString(1, name.name)
                setInt(2, club.id.id)
            }

            if (stmt.executeUpdate() == 0) {
                throw SQLException("Error creating a Court")
            }
            val keys = stmt.generatedKeys

            if(keys.next()){
                return Court(Id(keys.getInt(1)), name, club)
            }

            throw SQLException("Error creating a Court")
        }

    override fun getCourtById(crid: Id): Court? =
        dataSource.connection.use {
            val sql = buildString {
                append(sqlCourt)
                append(" INNER JOIN club ON court.club = club.cid ")
                append(" INNER JOIN users ON club.owner = users.uid ")
                append(" WHERE court.crid = ? ")
            }
            val stmt = it.prepareStatement(
                sql
            ).apply {
                setInt(1, crid.id)
            }
            val rs = stmt.executeQuery()
            if (rs.next()) {
                return rs.toCourt(rs.toClub(rs.toUser()))
            }
            return null
        }

    override fun getCourtByClubId(cid: Id): List<Court> =
        dataSource.connection.use {
            val courts = mutableListOf<Court>()
            val sql = buildString {
                append(sqlCourt)
                append(" INNER JOIN club ON court.club = club.cid" )
                append(" INNER JOIN users ON club.owner = users.uid ")
                append(" WHERE court.club = ? ")
            }
            val stmt = it.prepareStatement(
                sql
            ).apply {
                setInt(1, cid.id)
            }
            val rs = stmt.executeQuery()
            while (rs.next()) {
                courts.add(
                    rs.toCourt(rs.toClub(rs.toUser()))
                )
            }
            return courts
        }
}