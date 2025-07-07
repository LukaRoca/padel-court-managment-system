package pt.isel.ls.webServices

import kotlinx.serialization.internal.InlinePrimitiveDescriptor
import pt.isel.ls.domain.*
import pt.isel.ls.data.Data
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Token
import pt.isel.ls.utils.exceptions.BadRequestException
import pt.isel.ls.webApi.models.court.CourtCreate
import pt.isel.ls.webApi.models.court.CourtDetails
import pt.isel.ls.webApi.models.court.CourtListResponse
import pt.isel.ls.webApi.models.court.CourtResponse
import java.awt.image.DataBufferInt
import java.util.NoSuchElementException
import java.util.UUID

open class CourtServices (private val db: Data) : ServicesSchema(db) {
    fun createCourt(courtCreate: CourtCreate, club : Int,token: UUID) : CourtResponse =
        withAuthorization(token) {
            if (db.court.getCourtByName(Name(courtCreate.name)) != null) {
                throw BadRequestException("The name of a court has to be unique")
            }

            val club = db.court.createCourt(courtCreate, Id(club))

            return@withAuthorization CourtResponse(club)
        }

    fun getCourtById(
        crid: Id,
        token: UUID
    ) : CourtDetails =
        withAuthorization(token) {
            val court = db.court.getCourtById(crid)
                ?: throw NoSuchElementException("No Court with id ${crid.id} was found")
            return@withAuthorization CourtDetails(court)
    }

    fun getCourtsByClubId(
        cid : Id,
        token: UUID,
        limit : Int,
        skip : Int
    ): CourtListResponse =
        withAuthorization(token) {
            val courts = db.court.getCourtByClubId(cid, limit, skip)
            return@withAuthorization CourtListResponse(courts)
        }
}

