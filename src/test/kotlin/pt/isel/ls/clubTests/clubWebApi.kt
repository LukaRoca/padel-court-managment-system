package pt.isel.ls.clubTests

import kotlinx.serialization.json.Json
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import pt.isel.ls.domain.Club
import pt.isel.ls.storage.dataMem.ClubDataMem
import pt.isel.ls.webApi.dto.ClubInput
import pt.isel.ls.webApi.dto.ClubOutput
import pt.isel.ls.webApi.ClubWebApi
import pt.isel.ls.webServices.ClubServices
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ClubWebApiTests {

    private val db = ClubDataMem

    private val clubWebApi = ClubWebApi(ClubServices(db))

    @Test
    fun `should create club successfully`() {
        val clubDto = ClubInput(name = "Club 1")
        val request = Request(Method.POST, "/club")
            .header("Authorization", "Bearer 42449fc7-0006-458d-b4dc-324d5583f634")
            .body(Json.encodeToString(clubDto))
        val response = clubWebApi.appClubs(request)
        assertEquals(CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        val actualResponse = Json.decodeFromString<ClubOutput>(responseBody)
        assertTrue(actualResponse.cid.id > 0)
    }

    @Test
    fun `should return NOT_FOUND for a non-existent club ID`() {
        val request = Request(Method.GET, "/clubs/9999")
        val response = clubWebApi.appClubs(request)
        assertEquals(NOT_FOUND, response.status)
        assertEquals("\"Not found\"", response.bodyString()
        )
    }

    @Test
    fun `should return OK if clubs exist`() {
        val request = Request(Method.GET, "/clubs")
        val response = clubWebApi.appClubs(request)
        assertEquals(OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        val responseBody = response.bodyString()
        val clubs: List<Club> = Json.decodeFromString(responseBody)
        assertTrue(clubs.isNotEmpty(), "There should be at least one club")
    }
}