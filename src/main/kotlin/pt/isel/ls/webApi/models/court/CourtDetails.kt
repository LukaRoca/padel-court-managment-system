package pt.isel.ls.webApi.models.court

import pt.isel.ls.domain.Court
import pt.isel.ls.webApi.models.user.UserDetails

class CourtDetails private constructor(val id: Int, val name: String,val club: Int){
    companion object {
        operator fun invoke(court: Court) : CourtDetails {
            return CourtDetails( court.id.id, court.name.name, court.club.id )
        }
    }

    override operator fun equals(other : Any?) : Boolean {

        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CourtDetails

        if (id != other.id) return false
        if (name != other.name) return false
        if (club != other.club) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + club.hashCode()
        return result
    }
}