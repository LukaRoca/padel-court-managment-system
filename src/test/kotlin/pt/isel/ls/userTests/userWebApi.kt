package pt.isel.ls.userTests

import kotlin.test.Test
import kotlin.test.assertEquals
import org.http4k.core.Method.POST
import org.http4k.core.Request
import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import pt.isel.ls.storage.dataMem.UserDataMem
import pt.isel.ls.webApi.dto.UserInput
import pt.isel.ls.webApi.UserWebApi
import pt.isel.ls.webApi.dto.UserOutput
import pt.isel.ls.webServices.UserServices
import kotlin.test.assertFailsWith

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
    fun `create user with invalid email`() {
        val usDto = UserInput("Luka", "invalidemail")
        val request = Request(POST, "/users")
            .header("content-type", "application/json")
            .body(Json.encodeToString(usDto))
        val exception = assertFailsWith<IllegalArgumentException> {
            userWebAPI.createUser(request)
        }
        assertEquals("Email must have @ in it", exception.message)
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
}