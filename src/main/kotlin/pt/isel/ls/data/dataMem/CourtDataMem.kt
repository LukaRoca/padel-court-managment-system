package pt.isel.ls.data.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.data.data.CourtData
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name

class CourtDataMem(private val courts: DataMemMap<Court> = DataMemMap()) : CourtData {

    override fun createCourt(name: Name, club: Club): Court? {
        val newCourt = Court(Id(courts.nextId.get()), name, club)
        courts.map[courts.nextId.get()] = newCourt
        return newCourt
    }

    override fun getCourtByClubId(id: Id): List<Court>? = courts.map.values.filter { it.club.id == id }

    override fun getCourtById(id : Id) : Court? = courts.map[id.id]
}

