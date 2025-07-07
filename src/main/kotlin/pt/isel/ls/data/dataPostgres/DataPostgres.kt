package pt.isel.ls.data.dataPostgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.data.*
import pt.isel.ls.utils.postgres.runSQLScript


class DataPostgres(connectionString: String) : Data {
    private val dataSource = PGSimpleDataSource().apply { setURL(connectionString) }

    private fun conn() =
        dataSource.connection.also {
            it.autoCommit = false
        }

    fun create () {
        conn().runSQLScript("createSchema.sql")
    }

    fun delete() {
        conn().runSQLScript("delete.sql")
    }

    override fun reset() {
        conn().runSQLScript("reset.sql")
    }

    override val user: UserData = UserDataPostgres(::conn)

    override val club: ClubData = ClubDataPostgres(::conn)

    override val court: CourtData = CourtDataPostgres(::conn)

    override val rental: RentalData = RentalDataPostgres(::conn)
}