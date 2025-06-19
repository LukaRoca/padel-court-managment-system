package pt.isel.ls.utlis.postgres

import pt.isel.ls.data.data.RentalData
import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Court
import pt.isel.ls.domain.Rental
import pt.isel.ls.domain.User
import pt.isel.ls.utlis.Date
import pt.isel.ls.utlis.Duration
import pt.isel.ls.utlis.Email
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.Owner
import pt.isel.ls.utlis.Password
import pt.isel.ls.utlis.Token
import java.sql.ResultSet

/**
 * Converts a [ResultSet] into a [User] object.
 *
 * @return The converted [User] object.
 */
fun ResultSet.toUser(): User {
    return User(
        Id(getInt("uid")),
        Name(getString("name")),
        Email(getString("email")),
        Token(getString("token")),
        Password(getString("password"))
    )
}

/**
 * Converts a [ResultSet] into a [Club] object.
 *
 * @return The converted [Club] object.
 */
fun ResultSet.toClub(owner: User): Club {
    return Club(
        Id(getInt("c_id")),
        Name(getString("c_name")),
        Owner(owner)
    )
}

/**
 * Converts a [ResultSet] into a [Court] object.
 *
 * @return The converted [Court] object.
 */
fun ResultSet.toCourt(club: Club): Court {
    return Court(
        Id(getInt("cr_rid")),
        Name(getString("cr_name")),
        club
    )
}

/**
 * Converts a [ResultSet] into a [Rental] object.
 *
 * @return The converted [Rental] object.
 */
fun ResultSet.toRental(user: User, court: Court): Rental {
    return Rental(
        Id(getInt("r_id")),
        Date(getString("r_date")),
        Duration(getInt("r_initd"),
            getInt("r_end")),
        user,
        court
    )
}

