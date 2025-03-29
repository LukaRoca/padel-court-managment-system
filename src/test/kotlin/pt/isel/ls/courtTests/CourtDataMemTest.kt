package pt.isel.ls.courtTests

import org.junit.Test
import pt.isel.ls.domain.*
import pt.isel.ls.storage.dataMem.CourtDataMem
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CourtDataMemTest {

    @Test
    fun `Create Court Successfully`() {
        val court = CourtDataMem.createCourt(Name("Padel Court 2"), Id(1))
        assertEquals("Padel Court 2", court?.name?.name)
        assertEquals(1, court?.club?.id?.id)
    }

    @Test
    fun `Create Court with Nonexistent Club`() {
        assertFailsWith<IllegalArgumentException> {
            CourtDataMem.createCourt(Name("Padel Court 3"), Id(999))
        }
    }

    @Test
    fun `Get Court by ID`() {
        val court = CourtDataMem.getCourt(Id(1))
        assertEquals("Padel Court 1", court?.name?.name)
    }

    @Test
    fun `Get Court by Nonexistent ID`() {
        val court = CourtDataMem.getCourt(Id(999))
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