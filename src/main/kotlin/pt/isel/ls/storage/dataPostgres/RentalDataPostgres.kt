package pt.isel.ls.storage.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.RentalIStorage
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement


class RentalDataPostgres (private val connection : Connection) : RentalIStorage {

    private val userData = UserDataPostgres(connection)
    private val clubData = ClubDataPostgres(connection)
    private val courtData = CourtDataPostgres(connection)
    override fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: Token): Rental? {
        val club = clubData.getClubById(cid) ?: return throw IllegalArgumentException("No club with id $cid")
        val court = courtData.getCourtById(crid) ?: return throw IllegalArgumentException("No court with id $crid")
        val user = club.owner.user
        val sql = "INSERT INTO rental(date, initDuration, endDuration, usr, court) VALUES(?, ?, ?, ?, ?)"
        val statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).apply {
            setString(1, date.value)
            setInt(2, duration.initDuration)
            setInt(3, duration.endDuration)
            setInt(4, user.uid.id)
            setInt(5, court.id.id)
        }
        if (statement.executeUpdate() == 0) {
            throw SQLException("Error while creating a new rental.")
        }
        val keys = statement.generatedKeys
        keys.next()
        return Rental(Id(keys.getInt("rid")), date, duration, user, court)
    }

    override fun getRentalById(rentalId: Id): Rental? {
        val sql = "SELECT * FROM rental WHERE rid = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, rentalId.id)
            stmt.executeQuery().use { rs ->
                if (rs.next()) {
                    val court = courtData.getCourtById(Id(rs.getInt("court"))) ?: return null
                    val user = userData.getUserById(Id(rs.getInt("usr"))) ?: return null
                    return Rental(
                        Id(rs.getInt("rid")),
                        Date(rs.getString("date")),
                        Duration(rs.getInt("initDuration"), rs.getInt("endDuration")),
                        user,
                        court
                    )
                }
            }
        }
        return null
    }

    override fun getRentalList(cid: Id, crid: Id, date: Date): List<Rental>? {
        val sql = "SELECT * FROM rental WHERE usr = ? AND court = ? AND date = ?"
        val rentals = mutableListOf<Rental>()
        val club = clubData.getClubById(cid) ?: return throw IllegalArgumentException("No club with id $cid")
        val court = courtData.getCourtById(crid) ?: return throw IllegalArgumentException("No court with this id $crid")
        val user = club.owner.user
        connection.prepareStatement(sql).use {stmt ->
            stmt.setInt(1, user.uid.id)
            stmt.setInt(2, crid.id)
            stmt.setString(3, date.value)
            stmt.executeQuery().use { rs ->
                while (rs.next()) {
                    val rid = rs.getInt("rid")
                    val initDuration = rs.getInt("initDuration")
                    val endDuration = rs.getInt("endDuration")
                    rentals.add(Rental(Id(rid), date, Duration(initDuration, endDuration), user, court))
                }
            }
            return rentals
        }
    }

    override fun getRentalsOfUser(uid: Id): List<Rental>? {
        val sql = "SELECT * FROM rental WHERE usr = ?"
        val rentals = mutableListOf<Rental>()
        val user = userData.getUserById(uid) ?: return throw IllegalArgumentException("No user with id $uid")
        connection.prepareStatement(sql).use {stmt ->
            stmt.setInt(1, user.uid.id)
            stmt.executeQuery().use { rs ->
                while (rs.next()) {
                    val rid = rs.getInt("rid")
                    val date = rs.getString("date")
                    val initDuration = rs.getInt("initDuration")
                    val endDuration = rs.getInt("endDuration")
                    val court = courtData.getCourtById(Id(rs.getInt("court"))) ?: throw SQLException("No court with this id $rid")
                    rentals.add(Rental(Id(rid), Date(date), Duration(initDuration, endDuration), user, court))
                }
            }
            return rentals
        }
    }

    override fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int> {
        val rentals = getRentalList(cid, crid, date)
        val availableHours = mutableListOf<Int>()
        val occupiedHours = mutableSetOf<Int>()

        rentals?.forEach { rental ->
            val startHour = rental.duration.initDuration
            val endHour = rental.duration.endDuration
            for (hour in startHour until endHour) {
                occupiedHours.add(hour)
            }
        }
        for (hour in 7..21) {
            if (hour !in occupiedHours) {
                availableHours.add(hour)
            }
        }
        return availableHours
    }
}