package userTests

import kotlin.test.Test
import kotlin.test.assertEquals
import org.http4k.core.Method.POST
import org.http4k.core.Request
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Status
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.Routes
import pt.isel.ls.data.dataPostgres.ClubDataPostgres
import pt.isel.ls.data.dataPostgres.CourtDataPostgres
import pt.isel.ls.data.dataPostgres.RentalDataPostgres
import pt.isel.ls.data.dataPostgres.UserDataPostgres
import pt.isel.ls.data.data.Data
import pt.isel.ls.webApi.dto.UserInput
import pt.isel.ls.webApi.UserWebApi
import pt.isel.ls.webApi.WebApi
import pt.isel.ls.webServices.IServices
import pt.isel.ls.webServices.UserServices
import kotlin.test.assertTrue

class UserWebApiTests {
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
    private val userServices = UserServices(storage)
    private val userWebAPI = UserWebApi(userServices)

    @Test
    fun `create a valid user`() {
        val usDto = UserInput("Jaco", "bjaco@gmail.com", "password123")
        val request = Request(POST, "/users")
            .header("content-type", "application/json")
            .body(Json.encodeToString(usDto))
        val response = userWebAPI.createUser(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
    }

    @Test
    fun `get club by ID returns the club`() {
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val userId = 1 // Assuming this ID exists in the database
        val request = Request(Method.GET, "users/$userId")
        val response = app(request)
        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        assertTrue(responseBody.contains("Luka Roca"), "Response body should contain the club name")
    }

    @Test
    fun `get user by non-existent ID returns not found`() {
        val api = WebApi(IServices(db = storage))
        val app = Routes(api).app
        val userId = 9999
        val request = Request(Method.GET, "users/$userId")
        val response = app(request)
        assertEquals(NOT_FOUND, response.status)
    }

    @Test
    fun `create user with invalid email returns error`() {
        val usDto = UserInput("Jaco", "invalid-email", "password123")
        val request = Request(POST, "/users")
            .header("content-type", "application/json")
            .body(Json.encodeToString(usDto))
        val response = userWebAPI.createUser(request)
        assertEquals(BAD_REQUEST, response.status)
    }
}