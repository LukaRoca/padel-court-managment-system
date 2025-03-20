package pt.isel.ls.userTests

import kotlin.test.Test
import kotlin.test.assertEquals
import org.http4k.core.Method.POST
import org.http4k.core.Request
import kotlinx.serialization.json.Json
import org.http4k.core.Status.Companion.CREATED
import pt.isel.ls.storage.dataMem.UserDataMem
import pt.isel.ls.webApi.dto.UserInput
import pt.isel.ls.webApi.UserWebApi
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
}