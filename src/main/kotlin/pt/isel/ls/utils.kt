package pt.isel.ls

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Rental
import pt.isel.ls.domain.Token
import pt.isel.ls.domain.User
import pt.isel.ls.storage.iStorage.UserIStorage
import pt.isel.ls.webApi.TokenNotFoundException
import pt.isel.ls.webApi.dto.*


fun checkIfTokenInDb(token: Token, db: UserIStorage): Boolean {
    val user = db.getUserByToken(token)
    if(user == null) {
        throw TokenNotFoundException("No user found with that token")
    }
    else return true
}

fun isUserAuthorized(user: User, club: Club): Boolean {
    return user.uid == club.owner.user.uid
}


// Helper function to convert a Rental to RentalDetails DTO
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

// Helper function to map a list of Rentals to RentalDetails DTOs
 fun mapRentalsToDetailsList(rentals: List<Rental>): List<RentalDetails> {
    return rentals.map { mapRentalToDetails(it) }
}
