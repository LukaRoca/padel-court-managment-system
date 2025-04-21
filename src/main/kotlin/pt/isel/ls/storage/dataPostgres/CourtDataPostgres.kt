package pt.isel.ls.storage.dataPostgres
import pt.isel.ls.domain.*
import pt.isel.ls.isUserAuthorized
import pt.isel.ls.storage.dataMem.UserDataMem.getUserByToken
import pt.isel.ls.storage.iStorage.CourtIStorage
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

class CourtDataPostgres (private val dataSource: DataSource) : CourtIStorage{
    override fun createCourt(name: Name, club: Club): Court? {
        val sql = """
            INSERT INTO court(name, club) VALUES (?, ?)
        """.trimIndent()
        dataSource.connection.use {
            val stmt = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            stmt.setString(1, name.name)
            stmt.setInt(2, club.id.id)
            if (stmt.executeUpdate() == 0) {
                throw SQLException("Error creating a Court")
            }
            val keys = stmt.generatedKeys
            keys.next()
            return Court(Id(keys.getInt(1)), name, club)
        }
    }

    override fun getCourtById(crid: Id): Court? {
        val sql = """
            SELECT court.crid as cr_rid,
            court.name as cr_name,
            court.club as c_rid,
            club.cid as c_id,
            club.name as c_name,
            club.owner as c_owner,
            users.uid as u_id,
            users.token as u_token,
            users.name as u_name,
            users.email as u_email
            FROM court
            INNER JOIN club ON court.club = club.cid
            INNER JOIN users ON club.owner = users.uid
            WHERE court.crid = ?
        """.trimIndent()
        dataSource.connection.use {
            val stmt = it.prepareStatement(sql)
            stmt.setInt(1, crid.id)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                val club = Club(Id(rs.getInt("c_id")),
                    Name(rs.getString("c_name")),
                    Owner(
                        User(
                            Id(rs.getInt("u_id")),
                            Name(rs.getString("u_name")),
                            Email(rs.getString("u_email")),
                            Token(rs.getString("u_token"))
                        )
                    )
                )
                return Court(
                    Id(rs.getInt("cr_rid")),
                    Name(rs.getString("cr_name")),
                    club
                )
            }

        }
        return null
    }
    override fun getCourtByClubId(cid: Id): List<Court> {
        val sql = """
            SELECT court.crid as cr_rid,
            court.name as cr_name,
            court.club as c_rid,
            club.cid as c_id,
            club.name as c_name,
            club.owner as c_owner,
            users.uid as u_id,
            users.token as u_token,
            users.name as u_name,
            users.email as u_email
            FROM court
            INNER JOIN club ON court.club = club.cid
            INNER JOIN users ON club.owner = users.uid
            WHERE court.club = ?
        """.trimIndent()
         val courts = mutableListOf<Court>()
        dataSource.connection.use {
            val stmt = it.prepareStatement(sql)
            stmt.setInt(1, cid.id)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                courts.add(
                    Court (
                        Id(rs.getInt("cr_rid")),
                        Name(rs.getString("cr_name")),
                        Club(
                            Id(rs.getInt("c_id")),
                            Name(rs.getString("c_name")),
                            Owner(
                                User(
                                    Id(rs.getInt("u_id")),
                                    Name(rs.getString("u_name")),
                                    Email(rs.getString("u_email")),
                                    Token(rs.getString("u_token"))
                                )
                            )
                        )
                    )
                )
            }
        }
        return courts
    }
}