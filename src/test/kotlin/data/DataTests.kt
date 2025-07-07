package data

import pt.isel.ls.data.Data

sealed class DataTests(private val db: pt.isel.ls.data.Data) {

    protected val users = db.user
    protected val clubs = db.club
    protected val courts = db.court
    protected val rentals = db.rental

}