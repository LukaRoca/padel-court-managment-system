package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.data.Data
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Token
import pt.isel.ls.webApi.dto.CourtDetails
import pt.isel.ls.webApi.dto.UserDetails
import pt.isel.ls.webApi.models.court.CourtCreate
import java.util.UUID

open class CourtServices (private val db: Data) : ServicesSchema(db) {

    fun createCourt(courtCreate: CourtCreate, token: UUID) : CourtResponse =
        withAuthorization(token) {

        }
    fun getCourtById(crid: Id) : Court? {
        return db.court.getCourtById(crid)
    }
    fun getCourtsByClubId(cid : Id, limit : Int, skip : Int) : PaginatedResult<CourtDetails> {
        val courts = db.court.getCourtByClubId(cid) ?: throw NullPointerException("Court with id ${cid} not found")
        val newCourts = courts.map { courts ->
            CourtDetails(
                courts.id.id, courts.name.name, ClubDetails(
                    courts.club.id.id,
                    courts.club.name.name,
                    UserDetails(
                        courts.club.owner.user.uid.id,
                        courts.club.owner.user.name.name,
                        courts.club.owner.user.email.value,
                        courts.club.owner.user.token.token
                    )
                )
            )

        }
        if (courts != null) {
            return newCourts.paginateWithInfo(limit, skip)
        }
        else {
            throw IllegalArgumentException("No courts found for $cid")
        }
    }


}

