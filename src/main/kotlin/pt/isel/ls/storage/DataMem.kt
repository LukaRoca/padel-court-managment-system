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


    override fun getClubById(cid: Id): Club? {
        return clubs.find { it.id == cid }
    }

    override fun createCourt(name: Name, cid: Id): Court {
        val club = clubs.find { it.id == cid } ?: throw IllegalArgumentException("Club not found")
        val newCourt = Court(Id(crid++),name, club)
        courts.add(newCourt)
        return newCourt
    }

    override fun getCourt(crid: Id): Court? {
        return courts.find { it.id == crid }
    }
    override fun getCourtByClub(club: Club): List<Court> {
        return courts.filter { it.club.id.id == club.id.id }
    }

    // Função que encontra um usuário pelo ID
    override fun getUserById(userId: Id): User? {
        return users.keys.find { it.uid == userId }
    }

    override fun createUser(name: Name, email: Email): Pair<Int, String> {
        val token = UUID.randomUUID().toString()
        val newUser = User(Id(uid), name, email)
        uid++
        users.put(newUser,token)
        println(users)
        return Pair(newUser.uid.id,token)
    }


    override fun getUserByToken(token: String): User? {
        return users.entries.find { it.value == token }?.key
    }

    override fun createClub(name: Name, user: User): Club {
        val newClub = Club(Id(cid++), name, Owner(user))
        clubs.add(newClub)
        return newClub
    }

    override fun getClubs(): List<Club> {
        return clubs
    }

    override fun createRental(cid: Id, crid: Id, date: Date, duration: Duration, token: String): Rental? {
        val court = getCourt(crid) ?: return null
        val user = getUserById(cid) ?: return null
        val newRental = Rental(Id(rid++), date, duration, User(user.uid, user.name, user.email), court)
        rentals.add(newRental)
        return newRental
    }

    override fun getRentalById(rentalId: Id): Rental? {
        return rentals.find { it.rid == rentalId }
    }

    override fun getRentalList(cid: Id, crid: Id, date: Date): List<Rental> {

        return rentals.filter { it.user.uid == cid && it.court.id == crid && it.date == date }
    }

    override fun getRentalsOfUser(cid: Id): List<Rental> {
        return rentals.filter { it.user.uid == cid }
    }

    override fun getAvailableHours(cid: Id, crid: Id, date: Date): List<Int> {
        val rentals = getRentalList(cid, crid, date)
        val availableHours = mutableListOf<Int>()
        val occupiedHours = mutableSetOf<Int>()

        rentals.forEach { rental ->
            // Get the start hour from the rental duration
            val startHour = rental.duration.initDuration
            // Calculate the end hour by adding the duration hours
            val endHour = rental.duration.endDuration

            // Mark all hours in this rental as occupied
            for (hour in startHour until endHour) {
                occupiedHours.add(hour)
            }
        }

        // Check all hours from 7 AM to 9 PM (21:00)
        for (hour in 7..21) {
            // If the hour is not in occupied hours, it's available
            if (hour !in occupiedHours) {
                availableHours.add(hour)
            }
        }

        return availableHours
    }


}