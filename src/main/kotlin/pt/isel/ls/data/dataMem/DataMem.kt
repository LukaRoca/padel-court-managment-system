package pt.isel.ls.data.dataMem

import pt.isel.ls.data.data.ClubData
import pt.isel.ls.data.data.CourtData
import pt.isel.ls.data.data.Data
import pt.isel.ls.data.data.RentalData
import pt.isel.ls.data.data.UserData

class DataMem : Data, DataSchema() {
    override val user: UserData = UserDataMem(usersDb)

    override val club: ClubData = ClubDataMem(clubsDb)

    override val court: CourtData = CourtDataMem(courtsDb)

    override val rental: RentalData = RentalDataMem(rentalsDb)
}