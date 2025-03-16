package pt.isel.ls.storage
import pt.isel.ls.domain.*
import java.util.UUID

object DataMem : IStorage {
    // Têm que ser mapa para conseguir retornar o token
    private val users = mutableMapOf(
        Pair(User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com")), "42449fc7-0006-458d-b4dc-324d5583f634") // Isto é um exemplo
    )

    private var uid = 2
    private var cid = 2
    private var rid = 2
    private var crid = 2

    private val clubs = mutableListOf(
        Club(Id(1), Name("Padel N"), Owner(User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com"))))
    )

    private val rentals = mutableListOf<Rental>()
    private val courts = mutableListOf(
        Court(Id(1), Name("Padel Court 1"), Club(Id(1), Name("Padel N"), Owner(User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com"))))))


    override fun getClubById(cid: Int): Club? {
        return clubs.find { it.id.id == cid }
    }

    override fun createCourt(name: String, cid: Int): Court {
        val club = clubs.find { it.id.id == cid } ?: throw IllegalArgumentException("Club not found")
        val newCourt = Court(Id(crid++), Name(name), club)
        courts.add(newCourt)
        return newCourt
    }
    override fun getCourt(crid: Int): Court? {
        return courts.find { it.id.id == crid }
    }
    override fun getCourtByClub(club: Club): List<Court> {
        return courts.filter { it.club.id.id == club.id.id }
    }

    // Função que encontra um usuário pelo ID
    override fun getUserById(userId: Int): User? {
        return users.keys.find { it.uid.id == userId }
    }

    override fun createUser(name: String, email: String): Pair<Int, String> {
        val token = UUID.randomUUID().toString()
        val newUser = User(Id(uid), Name(name), Email(email))
        uid++
        users.put(newUser,token)
        println(users)
        return Pair(newUser.uid.id,token)
    }

    override fun getUserByToken(token: String): User? {
        return users.entries.find { it.value == token }?.key
    }

    override fun createClub(name: String, user: User): Club {
        val newClub = Club(Id(cid++), Name(name), Owner(user))
        clubs.add(newClub)
        return newClub
    }

    override fun getClubs(): List<Club> {
        TODO()
    }

    override fun createRental(cid: Int, crid: Int, date: Date, duration: Int, token: String): Rental? {
        val court = getCourt(crid) ?: return null
        val user = getUserById(cid) ?: return null
        val newRental = Rental(Id(rid++), date, Duration(duration), User(user.uid, user.name, user.email), court)
        rentals.add(newRental)
        return newRental
    }




}