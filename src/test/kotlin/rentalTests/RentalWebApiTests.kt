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
import pt.isel.ls.data.data.Data
import pt.isel.ls.webApi.WebApi
import pt.isel.ls.webApi.dto.RentalInput
import pt.isel.ls.webServices.*

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

    private val rentalServices = RentalServices(storage)
    private val api = Routes(WebApi(IServices(storage))).app

    @Test
    fun `create a valid rental`() {
        val rentalDto = RentalInput(cid = 2, crid = 2, date = "2021-06-03", initDuration = 5, endDuration = 6)
        val request = Request(Method.POST, "rental")
            .header("content-type", "application/json")
            .header("Authorization", "Bearer 6f1dab46-dc62-4f52-ac55-3afb43a41a19")
            .body(Json.encodeToString(rentalDto))
        val response = api(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains("id"), "Response body should contain the rental ID")
    }
    @Test
    fun `get rental by ID returns the rental`() {
        val rentalId = 2
        val request = Request(GET, "/rentals/$rentalId")
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains("2021-06-03"), "Response body should contain the rental date")
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
        val userId = 1
        val request = Request(GET, "/rentals/user/$userId?limit=10&skip=0")
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertTrue(response.bodyString().contains("id"))
    }

    @Test
    fun `get rentals of court returns rentals`() {
        val courtId = 2
        val request = Request(Method.GET, "/rentals/courts/$courtId?limit=10&skip=0")
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertTrue(response.bodyString().contains("id"))
    }

    @Test
    fun `get rentals with date returns rentals`() {
        val request = Request(Method.GET, "/rental/date?date=2021-06-03")
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertTrue(response.bodyString().contains("id"))
    }

    @Test
    fun `get available hours returns list`() {
        val request = Request(Method.GET, "/rentals/available?cid=2&crid=2&date=2021-06-03")
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertTrue(response.bodyString().contains("["))
    }
}