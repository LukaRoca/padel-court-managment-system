package pt.isel.ls.utils

import pt.isel.ls.data.Data
import pt.isel.ls.domain.User
import pt.isel.ls.webApi.dto.UserDetails
import pt.isel.ls.webApi.models.club.ClubCreate

fun initialData(storage: Data) {
    // Cria utilizador
    val userDetails = UserDetails(
        id = 1, // ou o id correto retornado pelo createUser
        name = "António Pimentel",
        email = "A51820@alunos.isel.pt",
        token = "TOKEN_DE_TESTE"
    )

    val user = User(
        uid = Id(userDetails.id),
        name = Name(userDetails.name),
        email = Email(userDetails.email),
        password = Password("HASHED_PASSWORD"),
        token = Token(userDetails.token)
    )

    // Cria clube
    storage.club.createClub(
        ClubCreate(
            name = Name("Challengers is average"),
            owner = userDetails,
        ),
        user = user
    )
}