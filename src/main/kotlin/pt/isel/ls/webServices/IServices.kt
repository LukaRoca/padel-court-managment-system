package pt.isel.ls.webServices

import pt.isel.ls.data.Data

class IServices (db : pt.isel.ls.data.Data) {
    val user = UserServices(db)
    val club = ClubServices(db)
    val court = CourtServices(db)
    val rental = RentalServices(db)
}