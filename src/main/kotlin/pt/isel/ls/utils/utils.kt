package pt.isel.ls.utils

import pt.isel.ls.domain.Rental
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
 fun mapRentalToDetails(rental: Rental): RentalDetails {
    return RentalDetails(
        rental.rid.id,
        rental.date.value,
        DurationDetails(
            rental.duration.initDuration,
            rental.duration.endDuration,
            rental.duration.hours
        ),
        UserDetails(
            rental.user.uid.id,
            rental.user.name.name,
            rental.user.email.value,
            rental.user.token.token
        ),
        CourtDetails(
            rental.court.id.id,
            rental.court.name.name,
            ClubDetails(
                rental.court.club.id.id,
                rental.court.club.name.name,
                UserDetails(
                    rental.user.uid.id,
                    rental.user.name.name,
                    rental.user.email.value,
                    rental.user.token.token
                )
            )
        )
    )
}

/**
 * Helper function to map a list of Rentals to RentalDetails DTOs
 */
fun mapRentalsToDetailsList(rentals: List<Rental>): List<RentalDetails> {
    return rentals.map { mapRentalToDetails(it) }
}
