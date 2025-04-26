package pt.isel.ls.webServices

import pt.isel.ls.PaginatedResult
import pt.isel.ls.domain.*
import pt.isel.ls.paginateWithInfo
import pt.isel.ls.storage.iStorage.IStorage
import pt.isel.ls.webApi.dto.ClubDetails
import pt.isel.ls.webApi.dto.CourtDetails
import pt.isel.ls.webApi.dto.UserDetails

open class CourtServices (private val db: IStorage) {

    fun createCourt(name : Name, cid : Id, token: Token) : Court? {
        val user = db.user.getUserByToken(token) ?: throw NullPointerException("User with token ${token} not found")
        val club = db.club.getClubById(cid) ?: throw NullPointerException("Club with id ${cid} not found")
        val existingCourts = db.court.getCourtByClubId(cid) ?: emptyList()
        for (court in existingCourts) {
            if (court.name == name) {
                throw IllegalArgumentException("Court with the same name already exists")
            }
        }
        if (club.owner.user != user) throw IllegalArgumentException("Id or token not valid")
        return db.court.createCourt(name, club)
    }
    fun getCourtById(crid: Id ) : Court? {
        return db.court.getCourtById(crid)
    }
    fun getCourtsByClubId(cid : Id, limit : Int, skip : Int) : PaginatedResult<CourtDetails> {
        val courts = db.court.getCourtByClubId(cid)?.map { courts ->
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
            return courts.paginateWithInfo(limit, skip)
        }
        else {
            throw IllegalArgumentException("No courts found for $cid")
        }
    }


}

