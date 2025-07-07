package pt.isel.ls.webApi.models.court

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.generateRandomString

@Serializable
data class CourtCreate(val name : String) {
    companion object Factory {
        fun create(
            name : String = generateRandomString()
        ) : CourtCreate {
            return CourtCreate(name)
        }
    }
}