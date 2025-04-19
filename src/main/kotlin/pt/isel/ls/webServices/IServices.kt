package pt.isel.ls.webServices

import pt.isel.ls.storage.iStorage.IStorage

class IServices (db : IStorage) {
    val user = UserServices(db)
    val club = ClubServices(db)
    val court = CourtServices(db)
    val rental = RentalServices(db)
}