package pt.isel.ls.webApi.dto


import kotlinx.serialization.Serializable
import pt.isel.ls.domain.Id

@Serializable
data class CourtInput(
    val name : String,
    val cid : Int
)

@Serializable
data class CourtOutput(
    val crid : Id
)