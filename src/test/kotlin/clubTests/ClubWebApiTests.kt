package clubTests

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CREATED
import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.Routes
import pt.isel.ls.storage.dataPostgres.*
import pt.isel.ls.storage.iStorage.IStorage
import pt.isel.ls.webApi.dto.ClubInput
import pt.isel.ls.webApi.*
import pt.isel.ls.webServices.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.text.get

class ClubWebApiTests {

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
    private val clubServices = ClubServices(storage)
    private val clubWebApi = ClubWebApi(clubServices)

    @Test
    fun `create a valid club`() {
        val clubDto = ClubInput(name = "Test Club")
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val request = Request(Method.POST, "club")
            .header("content-type", "application/json")
            .header("Authorization", "Bearer 6f1dab46-dc62-4f52-ac55-3afb43a41a19")
            .body(Json.encodeToString(clubDto))
        val response = app(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
    }

    @Test
    fun `get club by ID returns the club`() {
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val clubId = 1
        val request = Request(Method.GET, "clubs/$clubId")
        val response = app(request)
        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains("Padel Luka1"), "Response body should contain the club name")
    }

    @Test
    fun `get club by non-existent ID returns not found`() {
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val response = app(Request(Method.GET, "clubs/999"))
        assertEquals(Status.NOT_FOUND, response.status)
        assertTrue(response.bodyString().contains("not found", ignoreCase = true))
    }

    @Test
    fun `create club with invalid data returns error`() {
        val clubDto = ClubInput(name = "")
        val request = Request(Method.POST, "/clubs")
            .header("content-type", "application/json")
            .body(Json.encodeToString(clubDto))
        val response = clubWebApi.createClub(request)
        assertEquals(BAD_REQUEST, response.status)
    }

    @Test
    fun `get club by name`(){
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val clubName = "Padel Luka1"
        val request = Request(Method.GET, "clubs/name/$clubName")
        val response = app(request)
        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains(clubName), "Response body should contain the club name")
    }

    @Test
    fun `delete existing club by ID returns success`() {
        val clubId = 1
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val deleteRequest = Request(Method.DELETE, "clubd/$clubId")
            .header("Authorization", "Bearer 6f1dab46-dc62-4f52-ac55-3afb43a41a19")
        val deleteResponse = app(deleteRequest)
        assertEquals(Status.OK, deleteResponse.status)
        assertTrue(deleteResponse.bodyString().contains("deleted successfully", ignoreCase = true))
    }
}
