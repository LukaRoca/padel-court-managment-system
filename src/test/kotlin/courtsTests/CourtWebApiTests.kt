package courtsTests

import kotlin.test.Test
import kotlin.test.assertEquals
import org.http4k.core.Method.POST
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.NOT_FOUND
import kotlinx.serialization.json.Json
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.Routes
import pt.isel.ls.data.dataPostgres.*
<<<<<<< Updated upstream
import pt.isel.ls.data.Data
=======
import pt.isel.ls.data.data.Data
import pt.isel.ls.utlis.Email
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.Password
>>>>>>> Stashed changes
import pt.isel.ls.webApi.WebApi
import pt.isel.ls.webApi.dto.CourtInput
import pt.isel.ls.webServices.*
import java.util.UUID
import kotlin.test.assertTrue

class CourtWebApiTests {

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

    private fun uniqueSuffix() = UUID.randomUUID().toString().substring(0, 8)

    private fun createOwnerAndClub(): Pair<pt.isel.ls.domain.User, pt.isel.ls.domain.Club> {
        val suffix = uniqueSuffix()
        val owner = userStorage.createUser(
            Name("Owner $suffix"),
            Email("owner_$suffix@example.com"),
            Password("Password123")
        )
        val club = clubStorage.createClub(Name("Club $suffix"), owner)
            ?: throw IllegalStateException("Failed to create club for test")
        return owner to club
    }

    @Test
    fun `create a valid court`() {
        val (owner, club) = createOwnerAndClub()
        val courtDto = CourtInput(name = "Test Court ${uniqueSuffix()}", cid = club.id.id)
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val request = Request(POST, "/courts")
            .header("content-type", "application/json")
            .header("Authorization", "Bearer ${owner.token.token}")
            .body(Json.encodeToString(courtDto))
        val response = app(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
    }

    @Test
    fun `get court by ID returns the court`() {
        val (_, club) = createOwnerAndClub()
        val courtName = "Test Court ${uniqueSuffix()}"
        val createdCourt = courtStorage.createCourt(Name(courtName), club)
            ?: throw IllegalStateException("Failed to create court for test")
        val courtId = createdCourt.id.id
        val request = Request(GET, "/courts/$courtId")
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val response = app(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains(courtName), "Response body should contain the court name")
    }

    @Test
    fun `get court by non-existent ID returns not found`() {
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val response = app(Request(GET, "/courts/999"))
        assertEquals(NOT_FOUND, response.status)
        assertTrue(response.bodyString().contains("not found", ignoreCase = true))
    }

    @Test
    fun `create court with invalid data returns error`() {
        val courtDto = CourtInput(name = "", cid = 2)
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val request = Request(POST, "/courts")
            .header("content-type", "application/json")
            .body(Json.encodeToString(courtDto))
        val response = app(request)
        assertEquals(BAD_REQUEST, response.status)
    }


}