package pt.isel.ls.storage.dataPostgres

import pt.isel.ls.storage.iStorage.*
import javax.sql.DataSource

class DataPostgres(dataSource: DataSource) : IStorage {
    override val user: UserIStorage = UserDataPostgres(dataSource)
    override val club: ClubIStorage = ClubDataPostgres(dataSource)
    override val court: CourtIStorage = CourtDataPostgres(dataSource)
    override val rental: RentalIStorage = RentalDataPostgres(dataSource)
}