package pt.isel.ls.data.dataPostgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.data.data.*

class DataPostgres(connectionString: String) : Data {
    private val dataSource = PGSimpleDataSource().apply { setURL(connectionString) }

    override val user: UserData = UserDataPostgres(dataSource)

    override val club: ClubData = ClubDataPostgres(dataSource)

    override val court: CourtData = CourtDataPostgres(dataSource)

    override val rental: RentalData = RentalDataPostgres(dataSource)
}