package pt.isel.ls.webApi.dto


import kotlinx.serialization.Serializable

@Serializable
data class CourtInput(
    val name : String,
    val cid : Int
)

@Serializable
data class CourtOutput(
    val id : Int
)

@Serializable
data class CourtDetails(
    val id: Int,
    val name: String,
    val club: ClubDetails,
)