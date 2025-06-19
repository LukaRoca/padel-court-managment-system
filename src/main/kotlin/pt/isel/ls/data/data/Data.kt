package pt.isel.ls.data.data

interface Data {
    val user : UserData
    val club : ClubData
    val court : CourtData
    val rental : RentalData
}