package rentalTests

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Status.Companion.OK
import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.Routes
import pt.isel.ls.data.dataPostgres.*
<<<<<<< Updated upstream
import pt.isel.ls.data.Data
=======
import pt.isel.ls.data.data.Data
import pt.isel.ls.utlis.Date
import pt.isel.ls.utlis.Duration
import pt.isel.ls.utlis.Email
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.Password
>>>>>>> Stashed changes
import pt.isel.ls.webApi.WebApi
import pt.isel.ls.webApi.dto.RentalInput
import pt.isel.ls.webServices.*
import java.util.UUID

class RentalWebApiTests {

    private val dataSource = PGSimpleDataSource().apply {
        setURL("jdbc:postgresql://localhost/ls?user=postgres&password=tubarao")
    }

    private val userStorage = UserDataPostgres(dataSource)
    private val clubStorage = ClubDataPostgres(dataSource)
    private val courtStorage = CourtDataPostgres(dataSource)
    private val rentalStorage = RentalDataPostgres(dataSource)

    private val storage = object : Data {
        override val user = userStorage
        override val club = clubStorage
        override val court = courtStorage
        override val rental = rentalStorage
    }

    private val api = Routes(WebApi(IServices(storage))).app

    private data class Fixture(
        val user: pt.isel.ls.domain.User,
        val club: pt.isel.ls.domain.Club,
        val court: pt.isel.ls.domain.Court,
        val rental: pt.isel.ls.domain.Rental,
        val date: String,
    )

    private fun uniqueSuffix() = UUID.randomUUID().toString().substring(0, 8)

    private fun createFixture(date: String = "2026-01-01"): Fixture {
        val suffix = uniqueSuffix()
        val user = userStorage.createUser(
            Name("Rental User $suffix"),
            Email("rental_user_$suffix@example.com"),
            Password("Password123")
        )
        val club = clubStorage.createClub(Name("Rental Club $suffix"), user)
            ?: throw IllegalStateException("Failed to create club fixture")
        val court = courtStorage.createCourt(Name("Rental Court $suffix"), club)
            ?: throw IllegalStateException("Failed to create court fixture")
        val rental = rentalStorage.createRental(court, Date(date), Duration(5, 6), user)
            ?: throw IllegalStateException("Failed to create rental fixture")
        return Fixture(user, club, court, rental, date)
    }

    @Test
    fun `create a valid rental`() {
        val fixture = createFixture(date = "2026-01-02")
        val rentalDto = RentalInput(
            cid = fixture.club.id.id,
            crid = fixture.court.id.id,
            date = fixture.date,
            initDuration = 7,
            endDuration = 8
        )
        val request = Request(Method.POST, "/rental")
            .header("content-type", "application/json")
            .header("Authorization", "Bearer ${fixture.user.token.token}")
            .body(Json.encodeToString(rentalDto))
        val response = api(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains("id"), "Response body should contain the rental ID")
    }
    @Test
    fun `get rental by ID returns the rental`() {
        val fixture = createFixture(date = "2026-01-03")
        val rentalId = fixture.rental.rid.id
        val request = Request(GET, "/rentals/$rentalId")
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains(fixture.date), "Response body should contain the rental date")
    }

    @Test
    fun `get rental by non-existent ID returns NOT_FOUND`() {
        val request = Request(GET, "/rentals/999")
        val response = api(request)
        assertEquals(NOT_FOUND, response.status)
        assertTrue(response.bodyString().contains("not found", ignoreCase = true))
    }

    @Test
    fun `get rentals of user returns rentals`() {
        val fixture = createFixture(date = "2026-01-04")
        val userId = fixture.user.uid.id
        val request = Request(GET, "/rentals/user/$userId?limit=10&skip=0")
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertTrue(response.bodyString().contains("list"))
    }

    @Test
    fun `get rentals of court returns rentals`() {
        val fixture = createFixture(date = "2026-01-05")
        val courtId = fixture.court.id.id
        val request = Request(Method.GET, "/rentals/courts/$courtId?limit=10&skip=0")
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertTrue(response.bodyString().contains("list"))
    }

    @Test
    fun `get rentals with date returns rentals`() {
        val fixture = createFixture(date = "2026-01-06")
        val request = Request(Method.GET, "/rental/date?date=${fixture.date}")
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertTrue(response.bodyString().contains(fixture.date))
    }

    @Test
    fun `get available hours returns list`() {
        val fixture = createFixture(date = "2026-01-07")
        val request = Request(
            Method.GET,
            "/rentals/available?cid=${fixture.club.id.id}&crid=${fixture.court.id.id}&date=${fixture.date}"
        )
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertTrue(response.bodyString().contains("["))
    }


}