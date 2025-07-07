package pt.isel.ls.data.dataMem

import pt.isel.ls.data.ClubData
import pt.isel.ls.data.CourtData
import pt.isel.ls.data.Data
import pt.isel.ls.data.RentalData
import pt.isel.ls.data.UserData

class DataMem : pt.isel.ls.data.Data, DataSchema() {
    override val user: pt.isel.ls.data.UserData = UserDataMem(usersDb)

    override val club: pt.isel.ls.data.ClubData = ClubDataMem(clubsDb)

    override val court: pt.isel.ls.data.CourtData = CourtDataMem(courtsDb)

    override val rental: pt.isel.ls.data.RentalData = RentalDataMem(rentalsDb)
}