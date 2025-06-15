package pt.isel.ls.data.dataMem

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Court
import pt.isel.ls.domain.Rental
import pt.isel.ls.domain.User

abstract class DataSchema {
    val usersDb = DataMemMap<User>()

    val clubsDb = DataMemMap<Club>()

    val courtsDb = DataMemMap<Court>()

    val rentalsDb = DataMemMap<Rental>()
}