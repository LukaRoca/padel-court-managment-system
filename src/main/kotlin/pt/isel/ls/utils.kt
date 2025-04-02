package pt.isel.ls

import pt.isel.ls.domain.Token
import pt.isel.ls.storage.iStorage.UserIStorage


fun checkIfTokenInDb(token: Token, db: UserIStorage): Boolean {
    val user = db.getUserByToken(token)
    return user != null
}