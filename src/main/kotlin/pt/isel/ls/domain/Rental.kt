package pt.isel.ls.domain

import pt.isel.ls.utils.Date
import pt.isel.ls.utils.Duration
import pt.isel.ls.utils.Id

data class Rental (
    val rid : Id,
    val date : Date,
    val duration : Duration,
    val user : Id,
    val court : Id
)
