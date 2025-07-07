import pt.isel.ls.data.dataMem.ClubDataMem
import pt.isel.ls.data.dataMem.CourtDataMem
import pt.isel.ls.data.dataMem.DataSchema
import pt.isel.ls.data.dataMem.RentalDataMem
import pt.isel.ls.data.dataMem.UserDataMem
import pt.isel.ls.data.ClubData
import pt.isel.ls.data.CourtData
import pt.isel.ls.data.RentalData
import pt.isel.ls.data.UserData

class DataMem : pt.isel.ls.data.Data, DataSchema() {
    override fun reset() {
        usersDb.clear()
        clubsDb.clear()
        courtsDb.clear()
        rentalsDb.clear()
    }

    override val user: UserData = UserDataMem(usersDb)
    override val club: ClubData = ClubDataMem(clubsDb)
    override val court: CourtData = CourtDataMem(courtsDb)
    override val rental: RentalData = RentalDataMem(rentalsDb)
}