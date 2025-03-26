package pt.isel.ls.storage.dataPostgres

import pt.isel.ls.domain.Court
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.storage.dataMem.ClubDataMem.getClubById
import pt.isel.ls.storage.iStorage.CourtIStorage
import java.sql.Connection

class CourtDataPostgres (private val connection: Connection) : CourtIStorage{
    private var crid = 1
    override fun createCourt(name: Name, cid: Id): Court {
        val sql = "INSERT INTO courts(crid, name, cid) VALUES (?, ?, ?)"
        connection.prepareStatement(sql).use {
            it.setInt(1, crid)
            it.setString(2, name.name)
            it.setInt(3, cid.id)
            it.executeUpdate()
        }
        val club = getClubById(cid) ?: throw IllegalArgumentException("Club not found")
        crid++
        return Court(Id(crid),name, club)
    }
    override fun getCourt(id: Id): Court? {
        val sql = "SELECT * FROM courts WHERE crid = ?"
        connection.prepareStatement(sql).use {
            it.setInt(1, id.id)
            val rs = it.executeQuery()
            if(rs.next()){
                val name = rs.getString("name")
                val cid = rs.getInt("cid")
                val club = getClubById(Id(cid)) ?: throw IllegalArgumentException("Club not found")
                return Court(id, Name(name), club)
            }
        }
        return null
    }
    override fun getCourtByClubId(id: Id): List<Court> {
        val sql = "SELECT * FROM courts WHERE cid = ?"
        connection.prepareStatement(sql).use {
            it.setInt(1, id.id)
            val rs = it.executeQuery()
            val courts = mutableListOf<Court>()
            while (rs.next()) {
                val crid = rs.getInt("crid")
                val name = rs.getString("name")
                val club = getClubById(id) ?: throw IllegalArgumentException("Club not found")
                courts.add(Court(Id(crid), Name(name), club))
            }
            return courts
        }
    }
}