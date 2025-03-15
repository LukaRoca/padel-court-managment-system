package pt.isel.ls.storage
import pt.isel.ls.domain.*
import java.util.UUID

object DataMem : IStorage {
    private val users = mutableMapOf(
        Pair(
            User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com")),
            "42449fc7-0006-458d-b4dc-324d5583f634"
        )
    )
    private var uid = 2


    private val clubs = mutableListOf(
        Club(Id(1), Name("Padel N"), Owner(User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com"))))
    )
    private var cid = 2


    private val courts = mutableListOf(
        Court(
            Id(1),
            Name("Padel Court 1"),
            Club(Id(1), Name("Padel N"), Owner(User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com"))))
        )
    )
    private var crid = 2

    override fun createUser(name: String, email: String): Pair<Int, String> {
        val token = UUID.randomUUID().toString()
        val newUser = User(Id(uid), Name(name), Email(email))
        uid++
        users.put(newUser, token)
        println(users)
        return Pair(newUser.uid.id, token)
    }
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


    override fun createCourt(name: String, id: Id): Court {
        val club = clubs.find { it.id.id == id.id } ?: throw IllegalArgumentException("Club not found")
        val newCourt = Court(Id(crid++), Name(name), club)
        courts.add(newCourt)
        return newCourt
    }
    fun getCourt(crid: Int): Court? {
        return courts.find { it.id.id == crid }
    }
    fun getCourtsByClub(club: Club): List<Court> {
        return courts.filter { it.club.id.id == club.id.id }
    }
}

