package pt.isel.ls.storage.dataPostgres
import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Court
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.storage.dataMem.ClubDataMem.getClubById
import pt.isel.ls.storage.iStorage.CourtIStorage
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class CourtDataPostgres (private val connection: Connection) : CourtIStorage{

    private val clubData = ClubDataPostgres(connection)
    override fun createCourt(name: Name, cid: Id): Court? {
        val club = getClubById(cid) ?: return null
        val sql = "INSERT INTO courts(crid, name, cid) VALUES (?, ?, ?)"

        val statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).apply {
            setString(1, name.name)
            setInt(2, cid.id)
        }
        if (statement.executeUpdate() == 0) {
            throw SQLException("Error while creating a new court.")
        }
        val key = statement.generatedKeys
        key.next()
        return Court(
            Id(key.getInt("crid")), name, club = club)
    }

    override fun getCourt(id: Id): Court? {
        val sql = "SELECT * FROM courts WHERE crid = ?"

        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, id.id)
            stmt.executeQuery().use { result ->
                if (result.next()) {
                    val club = clubData.getClubById(cid = id) ?: throw IllegalArgumentException("Club not found")
                    return Court(
                        Id(result.getInt("crid")),
                        Name(result.getString("name")),
                        Club(club.id, name = club.name, owner = club.owner),
                    )
                }
            }
        }
        return null
    }
    override fun getCourtByClubId(cid: Id): List<Court> {
        val sql = "SELECT * FROM courts WHERE cid = ?"
        val courts = mutableListOf<Court>()
        connection.prepareStatement(sql).use {stmt ->
            stmt.executeQuery().use { result ->
                while(result.next()) {
                    val crid = result.getInt("crid")
                    val name = result.getString("name")
                    val club = clubData.getClubById(Id(result.getInt("cid"))) ?: throw IllegalArgumentException("Club not found")
                    courts.add(Court(Id(crid), Name(name), club))
                }
            }
            return courts
        }
    }
}