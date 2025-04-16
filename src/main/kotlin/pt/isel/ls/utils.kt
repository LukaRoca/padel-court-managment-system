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