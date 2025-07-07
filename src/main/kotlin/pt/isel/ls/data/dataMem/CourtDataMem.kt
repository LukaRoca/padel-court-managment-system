package pt.isel.ls.data.dataMem

import pt.isel.ls.domain.*
import pt.isel.ls.data.CourtData
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.webApi.models.court.CourtCreate
import kotlin.text.get

class CourtDataMem(private val courts: DataMemMap<Court> = DataMemMap()) : pt.isel.ls.data.CourtData {

    override fun createCourt(courtCreate: CourtCreate, club: Id): Court {
        val newCourt = Court(
            Id(courts.nextId.get()),
            Name(courtCreate.name),
            club
        )
        courts.map[courts.nextId.get()] = newCourt
        return newCourt
    }

    override fun getCourtByClubId(id: Id): List<Court>? = courts.map.values.filter { it.club.id == id.id }

    override fun getCourtById(id : Id) : Court? = courts.map[id.id]
}

