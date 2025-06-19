package data.clubs

import data.DataMemTests
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
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ClubMemTests : DataMemTests(), ClubTest {
    /*
    // Helper function to create a test user
    private fun createTestUser(name: String = "Test User", email: String = "test@example.com"): User {
        return users.createUser(
            Name(name),
            Email(email),
            Password("Password123")
        )
    }


     */
    @Test
    override fun createClubSuccessfully() {
        /*
        val user1 = createTestUser("User 1", "user1@example.com")
        val user2 = createTestUser("User 2", "user2@example.com")
        val user3 = createTestUser("User 3", "user3@example.com")
        
        val club1 = clubs.createClub(
            Name("Club 1"),
            user1
        )
        
        val club2 = clubs.createClub(
            Name("Club 2"),
            user2
        )
        
        val club3 = clubs.createClub(
            Name("Club 3"),
            user3
        )
        
        assertNotNull(club1)
        assertEquals("Club 1", club1?.name?.name)
        
        assertNotNull(club2)
        assertEquals("Club 2", club2?.name?.name)
        
        assertNotNull(club3)
        assertEquals("Club 3", club3?.name?.name)

         */
    }
    
    @Test
    override fun createClubFailed() {
        /*
        val user = createTestUser()
        
        // Test with invalid name (empty)
        assertFails { clubs.createClub(Name(""), user) }
        
        // Test with non-existent user ID
        val nonExistentUser = User(Id(999999), Name("Non-existent User"), Email("nonexistent@example.com"), Token("token"), Password("password"))
        assertFails { clubs.createClub(Name("Test Club"), nonExistentUser) }

         */
    }
    
    @Test
    override fun getClubByIdSuccessfully() {
        /*
        val user1 = createTestUser("User 1", "user1@example.com")
        val user2 = createTestUser("User 2", "user2@example.com")
        val user3 = createTestUser("User 3", "user3@example.com")
        
        val club1 = clubs.createClub(
            Name("Club 1"),
            user1
        )
        
        val club2 = clubs.createClub(
            Name("Club 2"),
            user2
        )
        
        val club3 = clubs.createClub(
            Name("Club 3"),
            user3
        )
        
        assertNotNull(club1)
        assertNotNull(club2)
        assertNotNull(club3)
        
        val retrievedClub1 = clubs.getClubById(club1!!.id)
        val retrievedClub2 = clubs.getClubById(club2!!.id)
        val retrievedClub3 = clubs.getClubById(club3!!.id)
        
        assertNotNull(retrievedClub1)
        assertEquals(club1.id, retrievedClub1?.id)
        assertEquals(club1.name, retrievedClub1?.name)
        
        assertNotNull(retrievedClub2)
        assertEquals(club2.id, retrievedClub2?.id)
        assertEquals(club2.name, retrievedClub2?.name)
        
        assertNotNull(retrievedClub3)
        assertEquals(club3.id, retrievedClub3?.id)
        assertEquals(club3.name, retrievedClub3?.name)

         */
    }
    
    @Test
    override fun getClubByIdFailed() {
        /*
        // Club with this ID doesn't exist
        assertNull(clubs.getClubById(Id(999999)))
        
        // Invalid ID
        assertFails { clubs.getClubById(Id(-1)) }

         */
    }
    
    @Test
    override fun getClubByNameSuccessfully() {
        /*
        val user1 = createTestUser("User 1", "user1@example.com")
        val user2 = createTestUser("User 2", "user2@example.com")
        
        val club1 = clubs.createClub(
            Name("Unique Club Name"),
            user1
        )
        
        val club2 = clubs.createClub(
            Name("Another Club Name"),
            user2
        )
        
        assertNotNull(club1)
        assertNotNull(club2)
        
        val retrievedClub1 = clubs.getClubByName(Name("Unique Club Name"))
        val retrievedClub2 = clubs.getClubByName(Name("Another Club Name"))
        
        assertNotNull(retrievedClub1)
        assertEquals(club1?.id, retrievedClub1?.id)
        assertEquals("Unique Club Name", retrievedClub1?.name?.name)
        
        assertNotNull(retrievedClub2)
        assertEquals(club2?.id, retrievedClub2?.id)
        assertEquals("Another Club Name", retrievedClub2?.name?.name)

         */
    }
    
    @Test
    override fun getClubByNameFailed() {
        /*
        // Club with this name doesn't exist
        assertNull(clubs.getClubByName(Name("Non-existent Club")))
        
        // Empty name
        assertFails { clubs.getClubByName(Name("")) }

         */
    }
    
    @Test
    override fun getClubsSuccessfully() {
        /*
        val user1 = createTestUser("User 1", "user1@example.com")
        val user2 = createTestUser("User 2", "user2@example.com")
        val user3 = createTestUser("User 3", "user3@example.com")
        
        val club1 = clubs.createClub(
            Name("Club 1"),
            user1
        )
        
        val club2 = clubs.createClub(
            Name("Club 2"),
            user2
        )
        
        val club3 = clubs.createClub(
            Name("Club 3"),
            user3
        )
        
        assertNotNull(club1)
        assertNotNull(club2)
        assertNotNull(club3)
        
        val allClubs = clubs.getClubs()
        
        assertNotNull(allClubs)
        assertTrue(allClubs.size >= 3)
        assertTrue(allClubs.any { it.name.name == "Club 1" })
        assertTrue(allClubs.any { it.name.name == "Club 2" })
        assertTrue(allClubs.any { it.name.name == "Club 3" })

         */
    }
    
    @Test
    override fun getClubsFailed() {
        /*
        // This test is for scenarios where getClubs might fail
        // For example, if we expect a certain number of clubs but get a different number
        
        val user1 = createTestUser("User 1", "user1@example.com")
        val user2 = createTestUser("User 2", "user2@example.com")
        
        clubs.createClub(Name("Club 1"), user1)
        clubs.createClub(Name("Club 2"), user2)
        
        val allClubs = clubs.getClubs()
        
        // Test should fail if we expect 1 club but have at least 2
        assertNotEquals(1, allClubs.size)

         */
    }
    
    @Test
    override fun deleteClubSuccessfully() {
        /*
        val user = createTestUser()
        
        val club = clubs.createClub(
            Name("Club to Delete"),
            user
        )
        
        assertNotNull(club)
        
        val result = clubs.deleteClub(club!!)
        
        assertTrue(result)
        
        // Verify the club is deleted
        assertNull(clubs.getClubById(club.id))

         */
    }
    
    @Test
    override fun deleteClubFailed() {
        /*
        val user = createTestUser()
        
        // Create a club that doesn't exist in the database
        val nonExistentClub = Club(Id(999999), Name("Non-existent Club"), Owner(user))
        
        // Attempt to delete a non-existent club should fail
        assertFails { clubs.deleteClub(nonExistentClub) }

         */
    }
}