package pt.isel.ls.webServices

import pt.isel.ls.data.Data

class IServices (val db : Data) {
    val user = UserServices(db)
    val club = ClubServices(db)
    val court = CourtServices(db)
    val rental = RentalServices(db)
}