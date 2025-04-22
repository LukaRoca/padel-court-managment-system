package pt.isel.ls

import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Token
import pt.isel.ls.domain.User
import pt.isel.ls.storage.iStorage.UserIStorage
import pt.isel.ls.webApi.TokenNotFoundException


fun checkIfTokenInDb(token: Token, db: UserIStorage): Boolean {
    val user = db.getUserByToken(token)
    if(user == null) {
        throw TokenNotFoundException("No user found with that token")
    }
    else return true
}

fun isUserAuthorized(user: User, club: Club): Boolean {
    return user.uid == club.owner.user.uid
}

fun Int?.validateInt(defaultValue: Int? = null, function: (Int) -> Boolean): Int {
    if (this == null) {
        if (defaultValue != null) {
            return defaultValue
        }
        throw IllegalArgumentException("Invalid argument id can't be null")
    }
    if (!function(this)) {
        throw IllegalArgumentException("Invalid argument: Int is not valid\nInt=$this")
    }
    return this
}

fun Int.isNotNegative(): Boolean {
    return this >= 0
}

const val DEFAULT_SKIP = 0
const val DEFAULT_LIMIT = 30

