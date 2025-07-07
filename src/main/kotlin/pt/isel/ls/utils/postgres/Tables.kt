package pt.isel.ls.utils.postgres

import kotlinx.serialization.internal.InlinePrimitiveDescriptor
import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Court
import pt.isel.ls.domain.Rental
import pt.isel.ls.domain.User
import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Owner
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token
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
fun ResultSet.toClub(): Club {
    return Club(
        Id(getInt("cid")),
        Name(getString("name")),
        Id(getInt("owner")),
        mutableListOf()
    )
}

/**
 * Converts a [ResultSet] into a [Court] object.
 *
 * @return The converted [Court] object.
 */
fun ResultSet.toCourt(): Court {
    return Court(
        Id(getInt("crid")),
        Name(getString("name")),
        Id(getInt("club"))
    )
}

/**
 * Converts a [ResultSet] into a [Rental] object.
 *
 * @return The converted [Rental] object.
 */
fun ResultSet.toRental(): Rental {
    return Rental(
        Id(getInt("rid")),
        Date(getString("date")),
        Duration(getInt("initDuration"),
            getInt("endDuration")),
        Id(getInt("usr")),
        Id(getInt("court"))
    )
}

