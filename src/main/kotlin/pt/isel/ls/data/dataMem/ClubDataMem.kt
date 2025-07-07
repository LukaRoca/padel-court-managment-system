package pt.isel.ls.data.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.data.ClubData
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Owner
import pt.isel.ls.webApi.models.club.ClubCreate
import kotlin.text.get
import kotlin.text.set

class ClubDataMem(private val clubs: DataMemMap<Club> = DataMemMap()) : ClubData {

    override fun createClub(clubCreate: ClubCreate, uid: Id): Club {
        val newClub = Club(
            Id(clubs.nextId.get()),
            Name(clubCreate.name),
            uid,
            mutableListOf()
        )
        clubs.map[clubs.nextId.get()] = newClub
        return newClub
    }

    override fun getClubById(cid: Id): Club? = clubs.map[cid.id]

    override fun getClubs(): List<Club> = clubs.map.values.toList()

    override fun getClubByName(name: Name): Club? = clubs.map.values.find { it.name == name }

    override fun deleteClub(club: Club): Boolean = clubs.map.remove(club.id.id) != null
}

