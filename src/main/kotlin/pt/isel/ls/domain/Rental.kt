package pt.isel.ls.domain

import pt.isel.ls.utlis.Date
import pt.isel.ls.utlis.Duration
import pt.isel.ls.utlis.Id

data class Rental (val rid : Id, val date : Date, val duration : Duration, val user : User, val court : Court)
