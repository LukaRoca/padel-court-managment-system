package pt.isel.ls.webApi.models.club

import pt.isel.ls.domain.Club
import pt.isel.ls.webApi.models.user.UserDetails


class ClubDetails private constructor(val id: Int, val name: String){
    companion object {
        operator fun invoke(club : Club) : ClubDetails {
            return ClubDetails(club.id.id, club.name.name)
        }
    }

    override operator fun equals(other : Any?) : Boolean {

        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserDetails

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        return result
    }
}