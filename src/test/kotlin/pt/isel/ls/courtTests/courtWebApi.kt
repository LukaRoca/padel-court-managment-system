package pt.isel.ls.courtTests

import kotlin.test.Test
import kotlin.test.assertEquals
import org.http4k.core.Method.POST
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.NOT_FOUND
import kotlinx.serialization.json.Json
import pt.isel.ls.domain.*
import pt.isel.ls.storage.dataMem.CourtDataMem
import pt.isel.ls.webApi.CourtWebApi
import pt.isel.ls.webApi.dto.CourtInput
import pt.isel.ls.webApi.dto.CourtOutput
import pt.isel.ls.webServices.*
import kotlin.test.assertTrue

class CourtWebApiTests {

    private val db = CourtDataMem

    private val courtWebApi = CourtWebApi(CourtServices(db))
    @Test
    fun `should create court successfully`() {
        val courtDto = CourtInput(id = 1, name = "Court 1")
        val request = Request(POST, "/courts")
            .header("Authorization", "Bearer 42449fc7-0006-458d-b4dc-324d5583f634")
            .body(Json.encodeToString(courtDto))
        val response = courtWebApi.createCourt(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        val actualResponse = Json.decodeFromString<CourtOutput>(responseBody)
        assertTrue(actualResponse.crid.id > 0)
    }

    @Test
    fun `should return NOT_FOUND for a non-existent club ID`() {
        val request = Request(GET, "/clubs/10/courts")
        val response = courtWebApi.appCourts(request)
        assertEquals(NOT_FOUND, response.status)
        assertEquals(
            "{\"error\":\"Club not found\"}",
            response.bodyString()
        )
    }

    @Test
    fun `should return OK if club has courts`() {
        val clubId = 1
        val request = Request(GET, "/clubs/$clubId/courts")
        val response = CourtWebApi(CourtServices(db)).appCourts(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        val courts: List<Court> = Json.decodeFromString(responseBody)
        assertTrue(courts.isNotEmpty(), "The club should have at least one court")
    }
}
