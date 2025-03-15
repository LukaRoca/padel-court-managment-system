package pt.isel.ls.storage
import pt.isel.ls.domain.*
import java.util.UUID

object DataMem : IStorage {
    // Têm que ser mapa para conseguir retornar o token
    private val users = mutableMapOf(
        Pair(User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com")), "42449fc7-0006-458d-b4dc-324d5583f634") // Isto é um exemplo
    )

    private var uid = 2

    private val clubs = mutableListOf(
        Club(Id(1), Name("Padel N"), Owner(User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com"))))
    )

    private var cid = 2

    override fun createUser(name: String, email: String): Pair<Int, String> {
        val token = UUID.randomUUID().toString()
        val newUser = User(Id(uid), Name(name), Email(email))
        uid++
        users.put(newUser,token)
        println(users)
        return Pair(newUser.uid.id,token)
    }

    // Função que encontra um usuário pelo ID
    override fun getUserById(userId: Int): User? {
        return users.keys.find { it.uid.id == userId }
    }

    override fun getUserByToken(token: String): User? {
        return users.entries.find { it.value == token }?.key
    }

    override fun createClub(name: String, user: User): Club {
        val newClub = Club(Id(cid++), Name(name), Owner(user))
        clubs.add(newClub)
        return newClub
    }


}
