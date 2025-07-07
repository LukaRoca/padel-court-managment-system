package pt.isel.ls.data

interface Data {
    fun reset()

    val user : UserData
    val club : ClubData
    val court : CourtData
    val rental : RentalData
}