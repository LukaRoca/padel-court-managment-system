package pt.isel.ls.data.dataPostgres

import pt.isel.ls.domain.*
import pt.isel.ls.data.RentalData
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.postgres.toClub
import pt.isel.ls.utils.postgres.toCourt
import pt.isel.ls.utils.postgres.toRental
import pt.isel.ls.utils.postgres.toUser
import pt.isel.ls.utils.postgres.useWithRollback
import pt.isel.ls.webApi.models.rental.RentalCreate
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

class RentalDataPostgres (private val conn: () -> Connection) : RentalData {
    override fun createRental(rentalCreate: RentalCreate, court: Id, user: Id): Rental =
        conn().useWithRollback {
            val (date, duration) = rentalCreate
            val sql = "INSERT INTO rental(date, initDuration, endDuration, usr, court) VALUES (?,?,?,?,?)"
            val stmt = it.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS
            ).apply {
                setString(1, date.value)
                setInt(2, duration.initDuration)
                setInt(3, duration.endDuration)
                setInt(4, user.id)
                setInt(5, court.id)
            }

            if (stmt.executeUpdate() == 0) {
                throw SQLException("Error while creating a new rental.")
            }

            val key = stmt.generatedKeys

            if (key.next()) {
                return Rental(Id(key.getInt(1)), date, duration, user, court)
            }

            throw SQLException("Error while creating a new rental.")
        }

    override fun getRentalById(rentalId: Id): Rental? = fetchRental("rid", rentalId.id)

    override fun getRentalsOfUser(user: Id): List<Rental> = fetchRentals("usr", user.id)

    override fun getRentals(): List<Rental> =
        conn().useWithRollback {
            val rentals = mutableListOf<Rental>()
            val sql = "SELECT * FROM rental"
            val stmt = it.prepareStatement(sql)
            val rs = stmt.executeQuery()

            while (rs.next()) {
                rentals.add(
                    rs.toRental()
                )
            }
            return rentals
        }

    override fun getRentalsOfCourt(court: Id): List<Rental> = fetchRentals("court", court.id)

    override fun getRentalsWithDate(date: Date): List<Rental> = fetchRentals("date", date.value)

    override fun getAvailableHours(court: Court, date: Date): List<Int>? {
        val rentals = getRentalsWithDate(date)
            .filter { it.court == court.id }
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

    override fun deleteRental(rental: Id): Boolean {
        conn().useWithRollback {
            val query = "DELETE FROM rental WHERE rid = ?"
            val stmt = it.prepareStatement(query).apply {
                setInt(1, rental.id)
            }
            val rs = stmt.executeUpdate()
            return rs > 0
        }
    }

    override fun updateRental(date: Date, duration: Duration, rental: Id): Boolean =
        conn().useWithRollback {
            val query = "UPDATE rental SET date = ?, initDuration = ?, endDuration = ? WHERE rid = ?"
            val stmt = it.prepareStatement(query).apply {
                setString(1, date.value)
                setInt(2, duration.initDuration)
                setInt(3, duration.endDuration)
                setInt(4, rental.id)
            }
            val rs = stmt.executeUpdate()
            return rs > 0
        }


    private fun fetchRental(identifier: String, value: Any): Rental? =
        conn().useWithRollback {
            val query = "SELECT * FROM rental WHERE $identifier = ?"

            val stmt = it.prepareStatement(query).apply {
                setObject(1, value)
            }

            val rs = stmt.executeQuery()

            if (rs.next()) {
                return rs.toRental()
            }

            return null
        }

    private fun fetchRentals(identifier: String, value: Any): List<Rental> =
        conn().useWithRollback {
            val rentals = mutableListOf<Rental>()
            val sql = "SELECT * FROM rental WHERE $identifier = ?"
            val stmt = it.prepareStatement(sql)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                rentals.add(
                    rs.toRental()
                )
            }
            return rentals
        }
}