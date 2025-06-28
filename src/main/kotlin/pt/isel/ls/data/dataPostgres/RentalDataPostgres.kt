package pt.isel.ls.data.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.data.data.RentalData
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.postgres.toClub
import pt.isel.ls.utils.postgres.toCourt
import pt.isel.ls.utils.postgres.toRental
import pt.isel.ls.utils.postgres.toUser
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

val sqlRental = """
    SELECT rental.rid as r_id,
        rental.date as r_date,
        rental.initDuration as r_initd,
        rental.endDuration as r_end,
        rental.usr as r_usr,
        rental.court as r_court,
        court.crid as cr_rid,
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
        FROM rental
""".trimIndent()

class RentalDataPostgres (private val dataSource : DataSource) : RentalData {

    override fun createRental(court: Court, date: Date, duration: Duration, user: User): Rental? =
        dataSource.connection.use {
            val sql = "INSERT INTO rental(date, initDuration, endDuration, usr, court) VALUES (?, ?, ?, ?, ?)"
            val stmt = it.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS
            ).apply {
                setString(1, date.value)
                setInt(2, duration.initDuration)
                setInt(3, duration.endDuration)
                setInt(4, user.uid.id)
                setInt(5, court.id.id)
            }

            if (stmt.executeUpdate() == 0) {
                throw SQLException("Could not create Rental")
            }

            val key = stmt.generatedKeys

            if(key.next()) {
                return Rental(Id(key.getInt(1)), date, duration, user, court)
            }

            throw SQLException("Could not create Rental")
        }

    override fun getRentalById(rentalId: Id): Rental? =
        dataSource.connection.use {
            val sql = buildString {
                append(sqlRental)
                append(" INNER JOIN court ON rental.court = court.crid ")
                append(" INNER JOIN club ON court.club = club.cid ")
                append(" INNER JOIN users ON rental.usr = users.uid ")
                append(" WHERE rental.rid = ? ")
            }

            val stmt = it.prepareStatement(
                sql
            ).apply {
                setInt(1, rentalId.id)
            }

            val rs = stmt.executeQuery()

            if (rs.next()) {
                val usr = rs.toUser()
                return rs.toRental(
                    usr,
                    rs.toCourt(
                        rs.toClub(
                            usr
                        )
                    )
                )
            }
            return null
        }

    override fun getRentalsOfUser(user : User): List<Rental>? =
        dataSource.connection.use {
            val rentals = mutableListOf<Rental>()
            val sql = buildString {
                append(sqlRental)
                append(" INNER JOIN court ON rental.court = court.crid ")
                append(" INNER JOIN club ON court.club = club.cid ")
                append(" INNER JOIN users ON rental.usr = users.uid ")
                append(" WHERE rental.usr = ? ")
            }
            val stmt = it.prepareStatement(
                sql
            ).apply {
                setInt(1, user.uid.id)
            }

            val rs = stmt.executeQuery()

            while (rs.next()) {
                val usr = rs.toUser()
                rentals.add(
                    rs.toRental(
                        usr,
                        rs.toCourt(
                            rs.toClub(
                                usr
                            )
                        )
                    )
                )
            }
            return rentals
        }

    override fun getRentals(club: Club, court: Court, date: Date): List<Rental>? =
        dataSource.connection.use {
            val rentals = mutableListOf<Rental>()
            val sql = buildString {
                append(sqlRental)
                append(" INNER JOIN court ON rental.court = court.crid ")
                append(" INNER JOIN club ON court.club = club.cid ")
                append(" INNER JOIN users ON rental.usr = users.uid ")
                append(" WHERE rental.court = ? ")
            }
            val stmt = it.prepareStatement(
                sql
            ).apply {
                setInt(1, court.id.id)
            }

            val rs = stmt.executeQuery()

            while (rs.next()) {
                rentals.add(
                    rs.toRental(
                        rs.toUser(),
                        court
                    )
                )
            }
            return rentals
        }

    override fun getRentalsOfCourt(court: Court): List<Rental>? =
        dataSource.connection.use {
            val rentals = mutableListOf<Rental>()
            val sql = buildString {
                append(sqlRental)
                append(" INNER JOIN court ON rental.court = court.crid ")
                append(" INNER JOIN club ON court.club = club.cid ")
                append(" INNER JOIN users ON rental.usr = users.uid ")
                append(" WHERE rental.court = ? ")
            }
            val stmt = it.prepareStatement(
                sql
            ).apply {
                setInt(1, court.id.id)
            }
            val rs = stmt.executeQuery()
            while (rs.next()) {
                rentals.add(
                    rs.toRental(
                        rs.toUser(),
                        court
                    )
                )
            }
            return rentals
        }

    override fun getRentalsWithDate(date: Date): List<Rental> =
        dataSource.connection.use {
            val rentals = mutableListOf<Rental>()
            val sql = buildString {
                append(sqlRental)
                append(" INNER JOIN court ON rental.court = court.crid ")
                append(" INNER JOIN club ON court.club = club.cid ")
                append(" INNER JOIN users ON rental.usr = users.uid ")
                append(" WHERE rental.date = ? ")
            }
            val stmt = it.prepareStatement(
                sql
            ).apply {
                setString(1, date.value)
            }
            val rs = stmt.executeQuery()
            while (rs.next()) {
                val usr = rs.toUser()
                rentals.add(
                    rs.toRental(
                        usr,
                        rs.toCourt(
                            rs.toClub(
                                usr
                            )
                        )
                    )
                )
            }
            return rentals
        }


    override fun getAvailableHours(club: Club, court: Court, date: Date): List<Int>? {
        val rentals = getRentalsWithDate(date)
            .filter { it.court.id == court.id && it.court.club.id == club.id }
        val availableHours = mutableListOf<Int>()
        val occupiedHours = mutableSetOf<Int>()

        rentals.forEach { rental ->
            val startHour = rental.duration.initDuration
            val endHour = rental.duration.endDuration
            for (hour in startHour until endHour) {
                occupiedHours.add(hour)
            }
        }
        for (hour in 0 until 24) {
            if (hour !in occupiedHours) {
                availableHours.add(hour)
            }
        }
        return availableHours
    }

    override fun deleteRental(rental : Rental): Boolean {
        val sql = """
            DELETE FROM rental
            WHERE rental.rid = ?
        """.trimIndent()
        dataSource.connection.use {
            val stmt = it.prepareStatement(sql)
            stmt.setInt(1, rental.rid.id)
            val rs = stmt.executeUpdate()
            if (rs > 0) {
                return true
            }
        }
        return false
    }

    override fun updateRental(date: Date, duration: Duration, rental: Rental): Rental? {
        val sql = """
            UPDATE rental
            SET date = ?, initDuration = ?, endDuration = ?
            WHERE rental.rid = ?
        """.trimIndent()

        dataSource.connection.use { conn ->
            val stmt = conn.prepareStatement(sql)
            stmt.setString(1, date.value)
            stmt.setInt(2, duration.initDuration)
            stmt.setInt(3, duration.endDuration)
            stmt.setInt(4, rental.rid.id)

            val rowsAffected = stmt.executeUpdate()
            return if (rowsAffected > 0) {
                rental.copy(date = date, duration = duration)
            } else {
                null
            }
        }
    }
}