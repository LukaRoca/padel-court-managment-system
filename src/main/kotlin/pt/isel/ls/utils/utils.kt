package pt.isel.ls.utils

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Court
import pt.isel.ls.domain.Rental
import pt.isel.ls.domain.User
import pt.isel.ls.webApi.dto.*

/**
 * Checks if an integer is not negative.
 *
 * @return `true` if the integer is not negative, `false` otherwise.
 */
fun Int.isNotNegative(): Boolean {
    return this >= 0
}

/**
 * Validates an integer based on a provided function. If the integer is null, it returns a default value or throws an exception.
 *
 * @param defaultValue The value to return if the integer is null.
 * @param function The function to validate the integer.
 * @return The integer if it's valid.
 * @throws IllegalArgumentException If the integer is null and no default value is provided, or if the integer is not valid.
 */
fun Int?.validateInt(defaultValue: Int? = null, function: (Int) -> Boolean): Int {
    if (this == null) {
        if (defaultValue != null) {
            return defaultValue
        }
        throw IllegalArgumentException("Invalid argument id can't be null")
    }
    if (!function(this)) {
        throw IllegalArgumentException("Invalid argument: Int is not valid\nInt=$this")
    }
    return this
}

/**
 * Helper function to convert a Rental to RentalDetails DTO
*/
fun mapRentalToDetails(
    rental: Rental,
    getUserById: (Id) -> User,
    getCourtById: (Id) -> Court,
    getClubById: (Id) -> Club,
): RentalDetails {
    val user = getUserById(rental.user)
    val court = getCourtById(rental.court)
    val club = getClubById(court.club)
    val clubOwner = getUserById(club.user)
    return RentalDetails(
        rental.rid.id,
        rental.date.value,
        DurationDetails(
            rental.duration.initDuration,
            rental.duration.endDuration,
            rental.duration.hours
        ),
        UserDetails(
            user.uid.id,
            user.name.name,
            user.email.value,
            user.token.toString()
        ),
        CourtDetails(
            court.id.id,
            court.name.name,
            ClubDetails(
                club.id.id,
                club.name.name,
                UserDetails(
                    club.user.id,
                    clubOwner.name.name,
                    clubOwner.email.value,
                    clubOwner.token.toString()
                )
            )
        )
    )
}

/**
 * Helper function to map a list of Rentals to RentalDetails DTOs
 */
fun mapRentalsToDetailsList(
    rentals: List<Rental>,
    courts: List<Court>,
    clubs: List<Club>,
    users: List<User>
): List<RentalDetails> {

    fun getUserById(id: Id): User =
        users.find { it.uid == id } ?: throw IllegalArgumentException("User with id $id not found")

    fun getCourtById(id: Id): Court =
        courts.find { it.id == id } ?: throw IllegalArgumentException("Court with id $id not found")

    fun getClubById(id: Id): Club =
        clubs.find { it.id == id } ?: throw IllegalArgumentException("Club with id $id not found")

    return rentals.map {
        mapRentalToDetails(
            rental = it,
            getUserById = ::getUserById,
            getCourtById = ::getCourtById,
            getClubById = ::getClubById
        )
    }
}
