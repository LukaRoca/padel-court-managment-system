package pt.isel.ls.data.dataPostgres
import pt.isel.ls.domain.*
import pt.isel.ls.data.CourtData
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.postgres.toClub
import pt.isel.ls.utils.postgres.toCourt
import pt.isel.ls.utils.postgres.toUser
import pt.isel.ls.utils.postgres.useWithRollback
import pt.isel.ls.webApi.models.court.CourtCreate
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

class CourtDataPostgres (private val conn: () -> Connection) : CourtData {
    override fun createCourt(
        courtCreate: CourtCreate,
        club : Id
    ): Court =
        conn().useWithRollback {
            val name = courtCreate
            val sql = "INSERT INTO court(name,club) VALUES (?,?)"
            val stmt = it.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS
            ).apply {
                setString(1, name.name)
                setInt(2, club.id)
            }

            if (stmt.executeUpdate() == 0) {
                throw SQLException("Error while creating a new court.")
            }

            val key = stmt.generatedKeys

            if (key.next()) {
                return Court(Id(key.getInt(1)), Name(name.name), Id(key.getInt("club")))
            }

            throw SQLException("Error while creating a new court")
        }

    override fun getCourtById(crid: Id): Court? = fetchCourt("crid", crid.id)

    override fun getCourtByClubId(cid: Id): List<Court> =
        conn().useWithRollback {
            val courts = mutableListOf<Court>()
            val query = "SELECT * FROM court WHERE club = ?"
            val stmt = it.prepareStatement(query).apply {
                setInt(1, cid.id)
            }
            val rs = stmt.executeQuery()
            while (rs.next()) {
                courts.add(
                    rs.toCourt()
                )
            }
            return courts
        }

    private fun fetchCourt(identifier : String, value: Any) : Court? {
        conn().useWithRollback {
            val query = "SELECT * FROM court WHERE $identifier=?"

            val stmt = it.prepareStatement(query).apply {
                setObject(1, value)
            }

            val rs = stmt.executeQuery()

            if (rs.next()) {
                return rs.toCourt()
            }

            return null
        }
    }
}