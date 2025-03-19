package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.CourtDataMem

object CourtServices {

    fun createCourt(name : Name, id : Id) : Court {
        return CourtDataMem.createCourt(name, id)
    }
    fun getCourtById(id : Id ) : Court? {
        return CourtDataMem.getCourt(id)
    }
    fun getCourtsByClub(club : Club) : List<Court> {
        return CourtDataMem.getCourtByClub(club)
    }

}

