package pt.isel.ls.userTests

import kotlin.test.Test
import kotlin.test.assertEquals
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.CREATED
import pt.isel.ls.domain.User
import pt.isel.ls.dto.ResponseCourtDto
import pt.isel.ls.dto.ResponseUserDto
import pt.isel.ls.dto.UserDTO
import pt.isel.ls.webApi.UserWebApi
import pt.isel.ls.webServices.UserServices
import kotlin.test.assertTrue

class UserWebApiTests {
    private val userWebAPI = UserWebApi(UserServices)

    @Test
    fun `create a valid user`() {
        val usDto = UserDTO("Jaco", "bjaco@gmail.com")
        val request = Request(POST, "/users")
            .header("content-type", "application/json")
            .body(Json.encodeToString(usDto))
        val response = userWebAPI.createUser(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
    }
}