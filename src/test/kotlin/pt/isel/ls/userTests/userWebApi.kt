package pt.isel.ls.userTests

import kotlin.test.Test
import kotlin.test.assertEquals
import org.http4k.core.Method.POST
import org.http4k.core.Request
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import pt.isel.ls.storage.dataMem.UserDataMem
import pt.isel.ls.webApi.dto.UserInput
import pt.isel.ls.webApi.UserWebApi
import pt.isel.ls.webApi.dto.UserOutput
import pt.isel.ls.webServices.UserServices

class UserWebApiTests {

    private val db = UserDataMem
    private val userWebAPI = UserWebApi(UserServices(db))

    @Test
    fun `create a valid user`() {
        val usDto = UserInput("Jaco", "bjaco@gmail.com")
        val request = Request(POST, "/users")
            .header("content-type", "application/json")
            .body(Json.encodeToString(usDto))
        val response = userWebAPI.createUser(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
    }

    @Test
    fun `get user by ID`() {
        val usDto = UserInput("Jaco", "bjaco@gmail.com")
        val createRequest = Request(POST, "/users")
            .header("content-type", "application/json")
            .body(Json.encodeToString(usDto))
        val createResponse = userWebAPI.app(createRequest)
        assertEquals(CREATED, createResponse.status)
        val createdUser = Json.decodeFromString<UserOutput>(createResponse.bodyString())
        val userId = createdUser.uid.id
        val getRequest = Request(Method.GET, "/users/$userId")
        val getResponse = userWebAPI.app(getRequest)
        assertEquals(OK, getResponse.status)
        assertEquals("application/json", getResponse.header("content-type"))
    }

    @Test
    fun `get user by invalid ID returns error`() {
        val getRequest = Request(Method.GET, "/users/abc")
        val getResponse = userWebAPI.app(getRequest)
        assertEquals(BAD_REQUEST, getResponse.status)
    }

    @Test
    fun `get user by non-existent ID returns not found`() {
        val getRequest = Request(Method.GET, "/users/99999")
        val getResponse = userWebAPI.app(getRequest)
        assertEquals(NOT_FOUND, getResponse.status)
    }

    @Test
    fun `create user with invalid email returns error`() {
        val usDto = UserInput("Jaco", "invalid-email")
        val request = Request(POST, "/users")
            .header("content-type", "application/json")
            .body(Json.encodeToString(usDto))
        val response = userWebAPI.createUser(request)
        assertEquals(BAD_REQUEST, response.status)
    }
}