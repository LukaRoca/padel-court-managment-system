package pt.isel.ls.webApi

import org.http4k.core.Method
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.webServices.IServices

class WebApi (services : IServices) {
    val user : UserWebApi = UserWebApi(services.user)
    val club : ClubWebApi = ClubWebApi(services.club)
    val court : CourtWebApi = CourtWebApi(services.court)
    val rental : RentalWebApi = RentalWebApi(services.rental)
}