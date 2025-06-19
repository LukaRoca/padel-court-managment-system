package pt.isel.ls.data.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.data.data.ClubData
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.Owner

class ClubDataMem(private val clubs: DataMemMap<Club> = DataMemMap()) : ClubData {

    override fun createClub(name: Name, user: User): Club? {
        val newClub = Club(Id(clubs.nextId.get()), name, Owner(user))
        clubs.map[clubs.nextId.get()] = newClub
        return newClub
    }

    override fun getClubById(cid: Id): Club? = clubs.map[cid.id]

    override fun getClubs(): List<Club> = clubs.map.values.toList()

    override fun getClubByName(name: Name): Club? = clubs.map.values.find { it.name == name }

    override fun deleteClub(club: Club): Boolean = clubs.map.remove(club.id.id) != null
}

