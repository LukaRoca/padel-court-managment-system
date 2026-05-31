package data.courts

import data.DataPostgresTests
import pt.isel.ls.domain.Court
import pt.isel.ls.domain.Club
import pt.isel.ls.domain.User
import pt.isel.ls.utlis.Email
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.Owner
import pt.isel.ls.utlis.Password
import pt.isel.ls.utlis.Token
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CourtPostgresTest : DataPostgresTests(), CourtTest {

    // Helper function to generate unique names
    private fun uniqueName(base: String): String {
        val uniqueId = UUID.randomUUID().toString().substring(0, 8).replace("-", "")
        return "$base $uniqueId"
    }
    
    // Helper function to generate unique email addresses
    private fun uniqueEmail(base: String): String {
        val uniqueId = UUID.randomUUID().toString().substring(0, 8).replace("-", "")
        return base.replace("@", "_$uniqueId@")
    }
    
    // Helper function to create a test user
    private fun createTestUser(name: String = "Test User", email: String = "test@example.com"): User {
        val uniqueName = uniqueName(name)
        val uniqueEmail = uniqueEmail(email)
        return users.createUser(
            Name(uniqueName),
            Email(uniqueEmail),
            Password("Password123")
        )
    }
    
    // Helper function to create a test club
    private fun createTestClub(name: String = "Test Club", user: User): Club {
        val uniqueName = uniqueName(name)
        val club = clubs.createClub(
            Name(uniqueName),
            user
        )
        return club ?: throw IllegalStateException("Failed to create club")
    }



    @Test
    override fun createCourtSuccessfully() {

        val user = createTestUser()
        val club = createTestClub(user = user)
        
        val courtName1 = uniqueName("Court 1")
        val courtName2 = uniqueName("Court 2")
        val courtName3 = uniqueName("Court 3")
        
        val court1 = courts.createCourt(
            Name(courtName1),
            club
        )
        
        val court2 = courts.createCourt(
            Name(courtName2),
            club
        )
        
        val court3 = courts.createCourt(
            Name(courtName3),
            club
        )
        
        assertNotNull(court1)
        assertEquals(courtName1, court1?.name?.name)
        assertEquals(club.id, court1?.club?.id)
        
        assertNotNull(court2)
        assertEquals(courtName2, court2?.name?.name)
        assertEquals(club.id, court2?.club?.id)
        
        assertNotNull(court3)
        assertEquals(courtName3, court3?.name?.name)
        assertEquals(club.id, court3?.club?.id)


    }
    
    @Test
    override fun createCourtFailed() {

        val user = createTestUser()
        val club = createTestClub(user = user)
        
        // Test with invalid name (empty)
        assertFails { courts.createCourt(Name(""), club) }
        
        // Test with non-existent club ID
        val nonExistentClub = Club(Id(999999), Name("Non existent Club"), Owner(user))
        assertFails { courts.createCourt(Name("Test Court"), nonExistentClub) }


    }
    
    @Test
    override fun getCourtByIdSuccessfully() {

        val user = createTestUser()
        val club = createTestClub(user = user)
        
        val courtName1 = uniqueName("Court 1")
        val courtName2 = uniqueName("Court 2")
        val courtName3 = uniqueName("Court 3")
        
        val court1 = courts.createCourt(
            Name(courtName1),
            club
        )
        
        val court2 = courts.createCourt(
            Name(courtName2),
            club
        )
        
        val court3 = courts.createCourt(
            Name(courtName3),
            club
        )
        
        assertNotNull(court1)
        assertNotNull(court2)
        assertNotNull(court3)
        
        val retrievedCourt1 = courts.getCourtById(court1!!.id)
        val retrievedCourt2 = courts.getCourtById(court2!!.id)
        val retrievedCourt3 = courts.getCourtById(court3!!.id)
        
        assertNotNull(retrievedCourt1)
        assertEquals(court1.id, retrievedCourt1?.id)
        assertEquals(court1.name, retrievedCourt1?.name)
        assertEquals(court1.club.id, retrievedCourt1?.club?.id)
        
        assertNotNull(retrievedCourt2)
        assertEquals(court2.id, retrievedCourt2?.id)
        assertEquals(court2.name, retrievedCourt2?.name)
        assertEquals(court2.club.id, retrievedCourt2?.club?.id)
        
        assertNotNull(retrievedCourt3)
        assertEquals(court3.id, retrievedCourt3?.id)
        assertEquals(court3.name, retrievedCourt3?.name)
        assertEquals(court3.club.id, retrievedCourt3?.club?.id)


    }
    
    @Test
    override fun getCourtByIdFailed() {

        // Court with this ID doesn't exist
        assertNull(courts.getCourtById(Id(999999)))
        
        // Invalid ID
        assertFails { courts.getCourtById(Id(-1)) }


    }
    
    @Test
    override fun getCourtsByClubIdSuccessfully() {

        val user = createTestUser()
        val club1 = createTestClub("Club 1", user)
        val club2 = createTestClub("Club 2", user)
        
        val courtName1 = uniqueName("Court 1 Club 1")
        val courtName2 = uniqueName("Court 2 Club 1")
        val courtName3 = uniqueName("Court 1 Club 2")

        val court1 = courts.createCourt(Name(courtName1), club1)
        val court2 = courts.createCourt(Name(courtName2), club1)
        val court3 = courts.createCourt(Name(courtName3), club2)
        
        assertNotNull(court1)
        assertNotNull(court2)
        assertNotNull(court3)
        
        val club1Courts = courts.getCourtByClubId(club1.id)
        val club2Courts = courts.getCourtByClubId(club2.id)
        
        assertNotNull(club1Courts)
        assertEquals(2, club1Courts?.size)
        assertTrue(club1Courts?.any { it.name.name == courtName1 } == true)
        assertTrue(club1Courts?.any { it.name.name == courtName2 } == true)
        
        assertNotNull(club2Courts)
        assertEquals(1, club2Courts?.size)
        assertEquals(courtName3, club2Courts?.get(0)?.name?.name)


    }
    
    @Test
    override fun getCourtsByClubIdFailed() {

        // Club with this ID doesn't exist
        val nonExistentClubCourts = courts.getCourtByClubId(Id(999999))
        assertTrue(nonExistentClubCourts?.isEmpty() == true)
        
        // Invalid ID
        assertFails { courts.getCourtByClubId(Id(-1)) }


    }
}