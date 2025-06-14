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
        val rentalDto = RentalInput(cid = 1, crid = 1, date = "2025-04-30", initDuration = 5, endDuration = 6)
        val request = Request(Method.POST, "rental")
            .header("content-type", "application/json")
            .header("Authorization", "Bearer dbc70057-4a7c-4b1d-805c-6d52490a0a0c")
            .body(Json.encodeToString(rentalDto))
        val response = api(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains("id"), "Response body should contain the rental ID")
    }
    @Test
    fun `get rental by ID returns the rental`() {
        val rentalId = 1
        val request = Request(GET, "/rentals/$rentalId")
        val response = api(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains("2025-04-30"), "Response body should contain the rental date")
    }

    @Test
    fun `get rental by non-existent ID returns NOT_FOUND`() {
        val request = Request(GET, "/rentals/999")
        val response = api(request)
        assertEquals(NOT_FOUND, response.status)
        assertTrue(response.bodyString().contains("not found", ignoreCase = true))
    }
}