package pt.isel.ls.webServices

import pt.isel.ls.domain.Club
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Token
import pt.isel.ls.data.Data
import pt.isel.ls.utils.exceptions.BadRequestException
import pt.isel.ls.webApi.models.club.ClubCreate
import pt.isel.ls.webApi.models.club.ClubDetails
import pt.isel.ls.webApi.models.club.ClubListResponse
import pt.isel.ls.webApi.models.club.ClubResponse
import pt.isel.ls.webApi.models.club.ClubSearch
import java.util.NoSuchElementException
import java.util.UUID
import kotlin.io.path.FileVisitorBuilder

class ClubServices (private val db : Data) : ServicesSchema(db) {
    fun createClub(clubCreate: ClubCreate, token : UUID) : ClubResponse =
        withAuthorization(token) {
            if (db.club.getClubByName(Name(clubCreate.name)) != null) {
                throw BadRequestException("The name of a club has to be unique")
            }

            val user = db.user.getUserByToken(token) ?: throw BadRequestException("The token has to be a user")

            val club = db.club.createClub(clubCreate, user.uid)

            return@withAuthorization ClubResponse(club)
        }

    fun getClubById(
        clubId: Id,
        token : UUID
    ): ClubDetails =
        withAuthorization(token) {
            val club = db.club.getClubById(clubId)
                ?: throw NoSuchElementException("No Club with id ${clubId.id} was found")
            return@withAuthorization ClubDetails(club)
        }

    fun getClubs(
        searchParameters: ClubSearch,
        token : UUID,
        skip : Int,
        limit : Int
    ): ClubListResponse =
        withAuthorization(token) {
            val clubs = db.club.getClubs(searchParameters,limit, skip)
            return@withAuthorization ClubListResponse(clubs)
        }
}