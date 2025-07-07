package pt.isel.ls.data

import pt.isel.ls.domain.*
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.webApi.dto.ClubInput
import pt.isel.ls.webApi.models.club.ClubCreate

interface ClubData {
    fun createClub(clubCreate: ClubCreate, uid: Id) : Club

    fun getClubById(cid: Id): Club?

    fun getClubs(): List<Club>

    fun getClubByName(name: Name) : Club?

    fun deleteClub(club: Club): Boolean
}