package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class Rental (val rid : Id, val date : Date, val duration : Duration, val user : User, val court : Court)
