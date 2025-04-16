package pt.isel.ls.webServices
import pt.isel.ls.checkIfTokenInDb
import pt.isel.ls.domain.*
import pt.isel.ls.storage.iStorage.CourtIStorage
import pt.isel.ls.storage.iStorage.UserIStorage

open class CourtServices (private val db: CourtIStorage, private val userDb: UserIStorage) {
    fun createCourt(name : Name, id : Id, token: Token) : Court? {
        checkIfTokenInDb(token,userDb)
        val existingCourts = db.getCourtByClubId(id) ?: emptyList()
        for (court in existingCourts) {
            if (court.name == name) {
                throw IllegalArgumentException("Court with the same name already exists")
            }
        }
        return db.createCourt(name, id, token)
    }
    fun getCourtById(id : Id ) : Court? {
        return db.getCourtById(id)
    }
    fun getCourtsByClub(id : Id) : List<Court>? {
        return db.getCourtByClubId(id)
    }
}

