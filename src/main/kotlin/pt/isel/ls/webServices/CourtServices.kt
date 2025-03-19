package pt.isel.ls.webServices

import pt.isel.ls.domain.*
import pt.isel.ls.storage.DataMem

object CourtServices {

    fun createCourt(name : Name, id : Id) : Court {
        return DataMem.createCourt(name, id)
    }
    fun getCourt(id : Id ) : Court? {
        return DataMem.getCourt(id)
    }
    fun getCourtsByClub(club : Club) : List<Court> {
        return DataMem.getCourtByClub(club)
    }

}