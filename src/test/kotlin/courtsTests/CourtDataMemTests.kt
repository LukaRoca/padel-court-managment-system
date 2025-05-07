package courtsTests

import org.junit.Test
import pt.isel.ls.domain.*
import pt.isel.ls.storage.dataMem.CourtDataMem
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CourtDataMemTest {

    @Test
    fun `Create Court with Existent Club`() {
        val token = Token("dbc70057-4a7c-4b1d-805c-6d52490a0a0c")
        val court = CourtDataMem.createCourt(
            name = Name("Padel Court 2"),
            club = Club(Id(1), Name("Padel Club"), owner =Owner(user = User(Id(1), Name("Michael"), Email("Michael@gmail.com"),
                token = token))))
        assertNotNull(court)
        assertEquals("Padel Court 2", court.name.name)
        assertEquals(1, court.club.id.id)
    }

    @Test
    fun `Get Court by ID`() {
        val court = CourtDataMem.getCourtById(Id(1))
        assertEquals("Padel Court 1", court?.name?.name)
    }

    @Test
    fun `Get Court by Nonexistent ID`() {
        val court = CourtDataMem.getCourtById(Id(999))
        assertEquals(null, court)
    }

    @Test
    fun `Get Courts by Club ID`() {
        val courts = CourtDataMem.getCourtByClubId(Id(1))
        assertEquals(1, courts?.size)
        assertEquals("Padel Court 1", courts?.get(0)?.name?.name)
    }

    @Test
    fun `Get Courts by Nonexistent Club ID`() {
        val courts = CourtDataMem.getCourtByClubId(Id(999))
        assertEquals(0, courts?.size)
    }
}