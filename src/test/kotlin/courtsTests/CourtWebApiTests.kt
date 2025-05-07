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
import pt.isel.ls.storage.dataPostgres.*
import pt.isel.ls.storage.iStorage.IStorage
import pt.isel.ls.webApi.WebApi
import pt.isel.ls.webApi.dto.CourtInput
import pt.isel.ls.webServices.*
import kotlin.test.assertTrue

class CourtWebApiTests {

    private val dataSource = PGSimpleDataSource().apply {
        setURL("jdbc:postgresql://localhost/ls?user=postgres&password=tubarao")
    }

    private val userStorage = UserDataPostgres(dataSource)
    private val clubStorage = ClubDataPostgres(dataSource)
    private val courtStorage = CourtDataPostgres(dataSource)
    private val rentalStorage = RentalDataPostgres(dataSource)

    private val storage = object : IStorage {
        override val user = userStorage
        override val club = clubStorage
        override val court = courtStorage
        override val rental = rentalStorage
    }

    private val courtServices = CourtServices(storage)
    private val api = WebApi(IServices(db = storage))
    private val app = Routes(api).app

    @Test
    fun `create a valid court`() {
        val courtDto = CourtInput(name = "Test Court", cid = 1)
        val request = Request(POST, "/courts")
            .header("content-type", "application/json")
            .header("Authorization", "Bearer dbc70057-4a7c-4b1d-805c-6d52490a0a0c")
            .body(Json.encodeToString(courtDto))
        val response = app(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
    }

    @Test
    fun `get court by ID returns the court`() {
        val courtId = 1
        val request = Request(GET, "/courts/$courtId")
        val response = app(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains("Campo Luka1"), "Response body should contain the court name")
    }

    @Test
    fun `get court by non-existent ID returns not found`() {
        val response = app(Request(GET, "/courts/999"))
        assertEquals(NOT_FOUND, response.status)
        assertTrue(response.bodyString().contains("not found", ignoreCase = true))
    }

    @Test
    fun `create court with invalid data returns error`() {
        val courtDto = CourtInput(name = "", cid = 1)
        val request = Request(POST, "/courts")
            .header("content-type", "application/json")
            .body(Json.encodeToString(courtDto))
        val response = app(request)
        assertEquals(BAD_REQUEST, response.status)
    }
}