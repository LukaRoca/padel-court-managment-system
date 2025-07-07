package pt.isel.ls.utils

import pt.isel.ls.data.Data
import pt.isel.ls.domain.User
import java.util.UUID
import pt.isel.ls.webApi.models.club.ClubCreate

fun initialData(storage: Data) {
    // Cria utilizador
    val user = User(
        uid = Id(1),
        name = Name("António Pimentel"),
        email = Email("A51820@alunos.isel.pt"),
        token = UUID.randomUUID(), // ou o token correto
        password = Password("HASHED_PASSWORD")
    )

    // Adiciona o utilizador à base de dados em memória
    storage.user.createUser(
        pt.isel.ls.webApi.models.user.UserCreate(
            name = user.name,
            email = user.email,
            password = user.password
        )
    )

    // Cria clube
    storage.club.createClub(
        ClubCreate(
            name = "Challengers is average"
        ),
        uid = user.uid
    )
}