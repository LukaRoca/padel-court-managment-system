package clubTests

import pt.isel.ls.domain.*
import pt.isel.ls.storage.dataMem.ClubDataMem
import pt.isel.ls.storage.dataMem.UserDataMem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ClubDataMemTest {

    @Test
    fun `test createClub adds club correctly`() {
        val user = UserDataMem.createUser(Name("John Doe"), Email("john.doe@example.com"))
        val club = ClubDataMem.createClub(Name("Tennis Club"), user)
        assertNotNull(club)
        assertEquals("Tennis Club", club.name.name)
        assertEquals(user.uid, club.owner.user.uid)
    }

    @Test
    fun `test getClubById returns correct club`() {
        val user = UserDataMem.createUser(Name("Alice"), Email("alice@example.com"))
        val createdClub = ClubDataMem.createClub(Name("Chess Club"), user)
        assertNotNull(createdClub)
        val retrievedClub = ClubDataMem.getClubById(createdClub.id)
        assertNotNull(retrievedClub)
        assertEquals(createdClub.id, retrievedClub.id)
    }

    @Test
    fun `test getClubById returns null for non-existent club`() {
        val club = ClubDataMem.getClubById(Id(999))
        assertNull(club)
    }

    @Test
    fun `test getClubs returns list of clubs`() {
        val clubs = ClubDataMem.getClubs()
        assertTrue(clubs.isNotEmpty(), "There should be at least one club")
    }
}