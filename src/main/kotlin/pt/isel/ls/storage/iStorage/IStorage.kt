package pt.isel.ls.storage.iStorage

interface IStorage {
    val user : UserIStorage
    val club : ClubIStorage
    val court : CourtIStorage
    val rental : RentalIStorage
}