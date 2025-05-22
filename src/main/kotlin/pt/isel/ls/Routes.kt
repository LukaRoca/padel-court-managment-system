package pt.isel.ls

import org.http4k.core.Method
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.singlePageApp
import pt.isel.ls.webApi.WebApi

class Routes (api: WebApi) {
    private val userRoutes =
        routes(
            "users" bind Method.POST to api.user::createUser,
            "users/{id}" bind Method.GET to api.user::getUserById,
            "users" bind Method.GET to api.user::getAllUsers
        )
    private val clubRoutes = routes(
        "club" bind Method.POST to api.club::createClub,
        "clubs/{id}" bind Method.GET to api.club::getClubById,
        "clubs" bind Method.GET to api.club::getClubs,
        "clubs/name/{name}" bind Method.GET to api.club::getClubByName,
        "clubd/{id}" bind Method.DELETE to api.club::deleteClub,
    )
    private val courtRoutes = routes(
        "courts" bind Method.POST to api.court::createCourt,
        "courts/{id}" bind Method.GET to api.court::getCourtById,
        "clubs/{id}/courts" bind Method.GET to api.court::getCourtsByClub
    )

    private val rentalRoutes = routes(
        "rental" bind Method.POST to api.rental::createRental,
        "rentals/available" bind Method.GET to api.rental::getAvailableHours,
        "rentals/{id}" bind Method.GET to api.rental::getRentalById,
        "rentals/user/{id}" bind Method.GET to api.rental::getRentalsOfUser,
        "rentals" bind Method.GET to api.rental::getRentals,
        "rentals/courts/{crid}" bind Method.GET to api.rental::getRentalsOfCourt,
        "rentalsd/{id}" bind Method.DELETE to api.rental::deleteRental,
        "rentalsu/{id}" bind Method.PUT to api.rental::updateRental,
        "rental/date" bind Method.GET to api.rental::getRentalsWithDate
    )

    val app =
        routes(
            userRoutes,
            clubRoutes,
            courtRoutes,
            rentalRoutes,
            singlePageApp(
                ResourceLoader.Directory("static_content")
            )
        )
}