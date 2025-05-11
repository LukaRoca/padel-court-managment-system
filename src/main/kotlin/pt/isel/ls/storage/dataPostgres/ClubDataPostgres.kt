package pt.isel.ls.storage.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.ClubIStorage

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

class ClubDataPostgres (private val dataSource : DataSource): ClubIStorage {
    override fun getClubById(cid: Id): Club? {
        val sql = """
            SELECT club.cid as c_id,
            club.name as c_name,
            club.owner as o_id,
            users.uid as u_id,
            users.name as u_name,
            users.email as u_email,
            users.token as u_token
            FROM club
            INNER JOIN users ON club.owner = users.uid
            WHERE club.cid = ?
            """.trimIndent()

        dataSource.connection.use {
            val stmt = it.prepareStatement(sql)
            stmt.setInt(1, cid.id)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                val owner = User(Id(rs.getInt("u_id")), Name(rs.getString("u_name")),
                    Email(rs.getString("u_email")), Token(rs.getString("u_token")))
                return Club(
                    Id(rs.getInt("c_id")),
                    Name(rs.getString("c_name")),
                    Owner(owner)
                )
            }
        }
        return null
    }

    override fun createClub(name: Name, user: User) : Club? {
        val sql = """
            INSERT INTO club(name, owner) VALUES (?, ?)
        """.trimIndent()

        dataSource.connection.use {
            val stmt = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            stmt.setString(1,name.name)
            stmt.setInt(2, user.uid.id)
            if (stmt.executeUpdate() == 0) {
                throw SQLException("Error while creating a new club.")
            }
            val key = stmt.generatedKeys
            key.next()
            return Club(Id(key.getInt(1)), name, Owner(user))
        }
    }

    override fun getClubs(): List<Club> {
        val sql = """
            SELECT club.cid as c_id,
            club.name as c_name,
            club.owner as o_id,
            users.uid as u_id,
            users.name as u_name,
            users.email as u_email,
            users.token as u_token
            FROM club
            INNER JOIN users ON club.owner = users.uid
            """.trimIndent()
        val clubs = mutableListOf<Club>()
        dataSource.connection.use {
            val stmt = it.prepareStatement(sql)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                val owner = User(Id(rs.getInt("u_id")), Name(rs.getString("u_name")),
                    Email(rs.getString("u_email")), Token(rs.getString("u_token")))
                clubs.add(
                    Club(
                        Id(rs.getInt("c_id")),
                        Name(rs.getString("c_name")),
                        Owner(owner)
                    )
                )
            }
        }
        return clubs
    }

    override fun getClubByName(name: Name): Club? {
        val sql = """
        SELECT club.cid as c_id,
               club.name as c_name,
               club.owner as o_id,
               users.uid as u_id,
               users.name as u_name,
               users.email as u_email,
               users.token as u_token
        FROM club
        INNER JOIN users ON club.owner = users.uid
        WHERE club.name = ?
    """.trimIndent()

        dataSource.connection.use {
            val stmt = it.prepareStatement(sql)
            stmt.setString(1, name.name)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                val owner = User(
                    Id(rs.getInt("u_id")),
                    Name(rs.getString("u_name")),
                    Email(rs.getString("u_email")),
                    Token(rs.getString("u_token"))
                )
                return Club(
                    Id(rs.getInt("c_id")),
                    Name(rs.getString("c_name")),
                    Owner(owner)
                )
            }
        }
        return null
    }
}