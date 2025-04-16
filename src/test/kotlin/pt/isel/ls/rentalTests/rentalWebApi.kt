package pt.isel.ls.rentalTests
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.http4k.core.Method.POST
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import kotlinx.serialization.json.Json
import pt.isel.ls.storage.dataMem.RentalDataMem
import pt.isel.ls.storage.dataMem.UserDataMem
import pt.isel.ls.webApi.RentalWebApi
import pt.isel.ls.webApi.dto.RentalInput
import pt.isel.ls.webApi.dto.RentalOutput
import pt.isel.ls.webServices.*

class RentalWebApiTests {

    private val db = RentalDataMem
    private val userdb = UserDataMem
    private val rentalWebApi = RentalWebApi(
        RentalServices(
            db,
            userDb = userdb
        )
    )

    @Test
    fun `should create rental successfully`() {
        val rentalDto = RentalInput(cid = 1, crid = 1, date = "2023-10-10", initDuration = 10, endDuration = 20)
        val request = Request(POST, "/rentals")
            .header("Authorization", "Bearer 42449fc7-0006-458d-b4dc-324d5583f634")
            .body(Json.encodeToString(rentalDto))
        val response = rentalWebApi.createRental(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        val actualResponse = Json.decodeFromString<RentalOutput>(responseBody)
        assertTrue(actualResponse.rid.id > 0)
    }

    @Test
    fun `should return NOT_FOUND for a non-existent rental ID`() {
        val request = Request(GET, "/rentals/10")
        val response = rentalWebApi.appRental(request)
        assertEquals(NOT_FOUND, response.status)
        assertEquals("application/json", response.header("content-type"))
        val expectedResponse = "\"Not found\""
        assertEquals(expectedResponse, response.bodyString())
    }
}