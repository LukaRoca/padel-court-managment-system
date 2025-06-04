    package pt.isel.ls.storage.dataPostgres

    import pt.isel.ls.domain.*
    import pt.isel.ls.storage.iStorage.RentalIStorage
    import java.sql.SQLException
    import java.sql.Statement
    import javax.sql.DataSource

    val sqlselect = """
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
                users.uid as u_id,
                users.token as u_token,
                users.name as u_name,
                users.email as u_email,
                users.password as u_password
                FROM court
                INNER JOIN court ON rental.court = court.crid
                INNER JOIN club ON court.club = club.cid
                INNER JOIN users ON rental.usr = users.id
            """.trimIndent()

    class RentalDataPostgres (private val dataSource : DataSource) : RentalIStorage {
        override fun getRentalById(rentalId: Id): Rental? {
            val sql = """
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
                users.uid as u_id,
                users.token as u_token,
                users.name as u_name,
                users.email as u_email,
                users.password as u_password
                FROM rental
                INNER JOIN court ON rental.court = court.crid
                INNER JOIN club ON court.club = club.cid
                INNER JOIN users ON rental.usr = users.uid
                WHERE rental.rid = ?
            """.trimIndent()
            dataSource.connection.use {
                val stmt = it.prepareStatement(sql)
                stmt.setInt(1, rentalId.id)
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    return Rental(Id(rs.getInt("r_id")),
                        Date(rs.getString("r_date")),
                        Duration(rs.getInt("r_initd"), rs.getInt("r_end")),
                        User(
                            Id(rs.getInt("u_id")),
                            Name(rs.getString("u_name")),
                            Email(rs.getString("u_email")),
                            Token(rs.getString("u_token")),
                            Password(rs.getString("u_password"))
                        ),
                        Court(
                            Id(rs.getInt("cr_rid")),
                            Name(rs.getString("cr_name")),
                            Club(
                                Id(rs.getInt("c_id")),
                                Name(rs.getString("c_name")),
                                Owner(User(
                                    Id(rs.getInt("u_id")),
                                    Name(rs.getString("u_name")),
                                    Email(rs.getString("u_email")),
                                    Token(rs.getString("u_token")),
                                    Password(rs.getString("u_password"))
                                ))
                            )
                        )

                    )
                }
            }
            return null
        }

        override fun createRental(court: Court, date: Date, duration: Duration, user: User): Rental? {
            val sql = "INSERT INTO rental(date, initDuration, endDuration, usr, court) VALUES (?, ?, ?, ?, ?)"
            dataSource.connection.use {
                val stmt = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                stmt.setString(1, date.value)
                stmt.setInt(2, duration.initDuration)
                stmt.setInt(3, duration.endDuration)
                stmt.setInt(4, user.uid.id)
                stmt.setInt(5, court.id.id)
                if (stmt.executeUpdate() == 0) {
                    throw SQLException("Could not create Rental")
                }
                val key = stmt.generatedKeys
                key.next()
                return Rental(Id(key.getInt(1)), date, duration, user, court)
            }
        }

        override fun getRentalsOfUser(user : User): List<Rental>? {
            val rentals = mutableListOf<Rental>()
            val sql = """
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
                users.uid as u_id,
                users.token as u_token,
                users.name as u_name,
                users.email as u_email,
                users.password as u_password
                FROM rental
                INNER JOIN court ON rental.court = court.crid
                INNER JOIN club ON court.club = club.cid
                INNER JOIN users ON rental.usr = users.uid
                WHERE rental.usr = ?
            """.trimIndent()
            dataSource.connection.use {
                val stmt = it.prepareStatement(sql)
                stmt.setInt(1, user.uid.id)
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    rentals.add(
                        Rental(Id(rs.getInt("r_id")),
                            Date(rs.getString("r_date")),
                            Duration(rs.getInt("r_initd"), rs.getInt("r_end")),
                            User(
                                Id(rs.getInt("u_id")),
                                Name(rs.getString("u_name")),
                                Email(rs.getString("u_email")),
                                Token(rs.getString("u_token")),
                                Password(rs.getString("u_password"))
                            ),
                            Court(
                                Id(rs.getInt("cr_rid")),
                                Name(rs.getString("cr_name")),
                                Club(
                                    Id(rs.getInt("c_id")),
                                    Name(rs.getString("c_name")),
                                    Owner(User(
                                        Id(rs.getInt("u_id")),
                                        Name(rs.getString("u_name")),
                                        Email(rs.getString("u_email")),
                                        Token(rs.getString("u_token")),
                                        Password(rs.getString("u_password"))
                                    ))
                                )
                            )

                        )
                    )
                }
            }
            return rentals
        }

        override fun getRentals(club: Club, court: Court, date: Date): List<Rental>? {
            val rentals = mutableListOf<Rental>()
            val sql = """SELECT rental.rid as r_id,
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
            users.uid as u_id,
            users.token as u_token,
            users.name as u_name,
            users.email as u_email,
            users.password as u_password
            FROM rental
            INNER JOIN court ON rental.court = court.crid
            INNER JOIN club ON court.club = club.cid
            INNER JOIN users ON rental.usr = users.uid
            WHERE rental.court = ?
            """.trimIndent()

            dataSource.connection.use {
                val stmt = it.prepareStatement(sql)
                stmt.setInt(1, court.id.id)
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    rentals.add(
                        Rental(Id(rs.getInt("r_id")),
                            date,
                            Duration(rs.getInt("r_initd"), rs.getInt("r_end")),
                            User(
                                Id(rs.getInt("u_id")),
                                Name(rs.getString("u_name")),
                                Email(rs.getString("u_email")),
                                Token(rs.getString("u_token")),
                                Password(rs.getString("u_password"))
                            ),
                            court
                        )
                    )
                }
            }
            return rentals
        }

        override fun getRentalsOfCourt(court: Court): List<Rental>? {
            val sql = """SELECT rental.rid as r_id,
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
            users.uid as u_id,
            users.token as u_token,
            users.name as u_name,
            users.email as u_email,
            users.password as u_password
            FROM rental
            INNER JOIN court ON rental.court = court.crid
            INNER JOIN club ON court.club = club.cid
            INNER JOIN users ON rental.usr = users.uid
            WHERE rental.court = ?
            """.trimIndent()
            val rentals = mutableListOf<Rental>()
            dataSource.connection.use {
                val stmt = it.prepareStatement(sql)
                stmt.setInt(1, court.id.id)
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    rentals.add(
                        Rental(Id(rs.getInt("r_id")),
                            Date(rs.getString("r_date")),
                            Duration(rs.getInt("r_initd"), rs.getInt("r_end")),
                            User(
                                Id(rs.getInt("u_id")),
                                Name(rs.getString("u_name")),
                                Email(rs.getString("u_email")),
                                Token(rs.getString("u_token")),
                                Password(rs.getString("u_password"))
                            ),
                            court
                        )
                    )
                }
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

        override fun getRentalsWithDate(date: Date): List<Rental> {
            val rentals = mutableListOf<Rental>()
            val sql = """
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
               users.uid as u_id,
               users.token as u_token,
               users.name as u_name,
               users.email as u_email,
               users.password as u_password
        FROM rental
        INNER JOIN court ON rental.court = court.crid
        INNER JOIN club ON court.club = club.cid
        INNER JOIN users ON rental.usr = users.uid
        WHERE rental.date = ?
    """.trimIndent()
            dataSource.connection.use {
                val stmt = it.prepareStatement(sql)
                stmt.setString(1, date.value)
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    rentals.add(
                        Rental(
                            Id(rs.getInt("r_id")),
                            Date(rs.getString("r_date")),
                            Duration(rs.getInt("r_initd"), rs.getInt("r_end")),
                            User(
                                Id(rs.getInt("u_id")),
                                Name(rs.getString("u_name")),
                                Email(rs.getString("u_email")),
                                Token(rs.getString("u_token")),
                                Password(rs.getString("u_password"))
                            ),
                            Court(
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
                                            Token(rs.getString("u_token")),
                                            Password(rs.getString("u_password"))
                                        )
                                    )
                                )
                            )
                        )
                    )
                }
            }
            return rentals
        }

    }