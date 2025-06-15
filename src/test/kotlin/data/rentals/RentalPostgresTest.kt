package data.rentals

import data.DataPostgresTests
import pt.isel.ls.domain.Club
import pt.isel.ls.domain.Court
import pt.isel.ls.domain.Rental
import pt.isel.ls.domain.User
import pt.isel.ls.utlis.Date
import pt.isel.ls.utlis.Duration
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

class RentalPostgresTest : DataPostgresTests(), RentalTest {
    /*
    // Helper function to generate unique names
    private fun uniqueName(base: String): String {
        val uniqueId = UUID.randomUUID().toString().substring(0, 8).replace("-", "")
        return "$base-$uniqueId"
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
    
    // Helper function to create a test court
    private fun createTestCourt(name: String = "Test Court", club: Club): Court {
        val uniqueName = uniqueName(name)
        val court = courts.createCourt(
            Name(uniqueName),
            club
        )
        return court ?: throw IllegalStateException("Failed to create court")
    }

     */
    
    @Test
    override fun createRentalSuccessfully() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        val rental1 = rentals.createRental(
            court = court,
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = user
        )
        
        val rental2 = rentals.createRental(
            court = court,
            date = Date("2023-10-11"),
            duration = Duration(14, 16),
            user = user
        )
        
        assertNotNull(rental1)
        assertEquals("2023-10-10", rental1?.date?.value)
        assertEquals(10, rental1?.duration?.initDuration)
        assertEquals(12, rental1?.duration?.endDuration)
        assertEquals(court.id, rental1?.court?.id)
        assertEquals(user.uid, rental1?.user?.uid)
        
        assertNotNull(rental2)
        assertEquals("2023-10-11", rental2?.date?.value)
        assertEquals(14, rental2?.duration?.initDuration)
        assertEquals(16, rental2?.duration?.endDuration)
        assertEquals(court.id, rental2?.court?.id)
        assertEquals(user.uid, rental2?.user?.uid)

         */
    }
    
    @Test
    override fun createRentalFailed() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        // Test with invalid date
        assertFails { 
            rentals.createRental(
                court = court,
                date = Date("invalid-date"),
                duration = Duration(10, 12),
                user = user
            ) 
        }
        
        // Test with invalid duration (end before start)
        assertFails { 
            rentals.createRental(
                court = court,
                date = Date("2023-10-10"),
                duration = Duration(12, 10),
                user = user
            ) 
        }
        
        // Test with non-existent court
        val nonExistentCourt = Court(Id(999999), Name("Non-existent Court"), club)
        assertFails { 
            rentals.createRental(
                court = nonExistentCourt,
                date = Date("2023-10-10"),
                duration = Duration(10, 12),
                user = user
            ) 
        }

         */
    }
    
    @Test
    override fun getRentalByIdSuccessfully() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        val rental = rentals.createRental(
            court = court,
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = user
        )
        
        assertNotNull(rental)
        
        val retrievedRental = rentals.getRentalById(rental!!.rid)
        
        assertNotNull(retrievedRental)
        assertEquals(rental.rid, retrievedRental?.rid)
        assertEquals(rental.date.value, retrievedRental?.date?.value)
        assertEquals(rental.duration.initDuration, retrievedRental?.duration?.initDuration)
        assertEquals(rental.duration.endDuration, retrievedRental?.duration?.endDuration)
        assertEquals(rental.court.id, retrievedRental?.court?.id)
        assertEquals(rental.user.uid, retrievedRental?.user?.uid)

         */
    }
    
    @Test
    override fun getRentalByIdFailed() {
        /*
        // Rental with this ID doesn't exist
        assertNull(rentals.getRentalById(Id(999999)))
        
        // Invalid ID
        assertFails { rentals.getRentalById(Id(-1)) }

         */
    }
    
    @Test
    override fun getRentalsOfUserSuccessfully() {
        /*
        val user1 = createTestUser("User 1", "user1@example.com")
        val user2 = createTestUser("User 2", "user2@example.com")
        val club = createTestClub(user = user1)
        val court = createTestCourt(club = club)
        
        val rental1 = rentals.createRental(
            court = court,
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = user1
        )
        
        val rental2 = rentals.createRental(
            court = court,
            date = Date("2023-10-11"),
            duration = Duration(14, 16),
            user = user1
        )
        
        val rental3 = rentals.createRental(
            court = court,
            date = Date("2023-10-12"),
            duration = Duration(9, 11),
            user = user2
        )
        
        assertNotNull(rental1)
        assertNotNull(rental2)
        assertNotNull(rental3)
        
        val user1Rentals = rentals.getRentalsOfUser(user1)
        val user2Rentals = rentals.getRentalsOfUser(user2)
        
        assertNotNull(user1Rentals)
        assertEquals(2, user1Rentals?.size)
        assertTrue(user1Rentals?.any { it.date.value == "2023-10-10" } == true)
        assertTrue(user1Rentals?.any { it.date.value == "2023-10-11" } == true)
        
        assertNotNull(user2Rentals)
        assertEquals(1, user2Rentals?.size)
        assertEquals("2023-10-12", user2Rentals?.get(0)?.date?.value)\

         */
    }
    
    @Test
    override fun getRentalsOfUserFailed() {
        /*
        // User with no rentals
        val user = createTestUser()
        val userRentals = rentals.getRentalsOfUser(user)
        
        assertNotNull(userRentals)
        assertTrue(userRentals?.isEmpty() == true)
        
        // Non-existent user
        val nonExistentUser = User(Id(999999), Name("Non-existent User"), Email("nonexistent@example.com"), Token("token"), Password("password"))
        assertFails { rentals.getRentalsOfUser(nonExistentUser) }

         */
    }
    
    @Test
    override fun getRentalsSuccessfully() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        val rental1 = rentals.createRental(
            court = court,
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = user
        )
        
        val rental2 = rentals.createRental(
            court = court,
            date = Date("2023-10-10"),
            duration = Duration(14, 16),
            user = user
        )
        
        assertNotNull(rental1)
        assertNotNull(rental2)
        
        val rentalsForDate = rentals.getRentals(club, court, Date("2023-10-10"))
        
        assertNotNull(rentalsForDate)
        assertEquals(2, rentalsForDate?.size)
        assertTrue(rentalsForDate?.any { it.duration.initDuration == 10 && it.duration.endDuration == 12 } == true)
        assertTrue(rentalsForDate?.any { it.duration.initDuration == 14 && it.duration.endDuration == 16 } == true)

         */
    }
    
    @Test
    override fun getRentalsFailed() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        // No rentals for this date
        val rentalsForDate = rentals.getRentals(club, court, Date("2023-12-25"))
        
        assertNotNull(rentalsForDate)
        assertTrue(rentalsForDate?.isEmpty() == true)
        
        // Invalid date
        assertFails { rentals.getRentals(club, court, Date("invalid-date")) }

         */
    }
    
    @Test
    override fun getRentalsOfCourtSuccessfully() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court1 = createTestCourt("Court 1", club)
        val court2 = createTestCourt("Court 2", club)
        
        val rental1 = rentals.createRental(
            court = court1,
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = user
        )
        
        val rental2 = rentals.createRental(
            court = court1,
            date = Date("2023-10-11"),
            duration = Duration(14, 16),
            user = user
        )
        
        val rental3 = rentals.createRental(
            court = court2,
            date = Date("2023-10-12"),
            duration = Duration(9, 11),
            user = user
        )
        
        assertNotNull(rental1)
        assertNotNull(rental2)
        assertNotNull(rental3)
        
        val court1Rentals = rentals.getRentalsOfCourt(court1)
        val court2Rentals = rentals.getRentalsOfCourt(court2)
        
        assertNotNull(court1Rentals)
        assertEquals(2, court1Rentals?.size)
        assertTrue(court1Rentals?.any { it.date.value == "2023-10-10" } == true)
        assertTrue(court1Rentals?.any { it.date.value == "2023-10-11" } == true)
        
        assertNotNull(court2Rentals)
        assertEquals(1, court2Rentals?.size)
        assertEquals("2023-10-12", court2Rentals?.get(0)?.date?.value)

         */
    }
    
    @Test
    override fun getRentalsOfCourtFailed() {
        /*
        // Court with no rentals
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        val courtRentals = rentals.getRentalsOfCourt(court)
        
        assertNotNull(courtRentals)
        assertTrue(courtRentals?.isEmpty() == true)
        
        // Non-existent court
        val nonExistentCourt = Court(Id(999999), Name("Non-existent Court"), club)
        assertFails { rentals.getRentalsOfCourt(nonExistentCourt) }

         */
    }
    
    @Test
    override fun getAvailableHoursSuccessfully() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        // Create some rentals for the court
        rentals.createRental(
            court = court,
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = user
        )
        
        rentals.createRental(
            court = court,
            date = Date("2023-10-10"),
            duration = Duration(14, 16),
            user = user
        )
        
        // Get available hours for the date
        val availableHours = rentals.getAvailableHours(club, court, Date("2023-10-10"))
        
        assertNotNull(availableHours)
        // Assuming business hours are 9-18, and we've booked 10-12 and 14-16
        assertTrue(availableHours?.contains(9) == true)
        assertTrue(availableHours?.contains(12) == true)
        assertTrue(availableHours?.contains(13) == true)
        assertTrue(availableHours?.contains(16) == true)
        assertTrue(availableHours?.contains(17) == true)
        
        // Hours that should not be available
        assertTrue(availableHours?.contains(10) == false)
        assertTrue(availableHours?.contains(11) == false)
        assertTrue(availableHours?.contains(14) == false)
        assertTrue(availableHours?.contains(15) == false)

         */
    }
    
    @Test
    override fun getAvailableHoursFailed() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        // Invalid date
        assertFails { rentals.getAvailableHours(club, court, Date("invalid-date")) }
        
        // Non-existent court
        val nonExistentCourt = Court(Id(999999), Name("Non-existent Court"), club)
        assertFails { rentals.getAvailableHours(club, nonExistentCourt, Date("2023-10-10")) }

         */
    }
    
    @Test
    override fun deleteRentalSuccessfully() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        val rental = rentals.createRental(
            court = court,
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = user
        )
        
        assertNotNull(rental)
        
        val result = rentals.deleteRental(rental!!)
        
        assertTrue(result)
        
        // Verify the rental is deleted
        assertNull(rentals.getRentalById(rental.rid))

         */
    }
    
    @Test
    override fun deleteRentalFailed() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        // Create a rental that doesn't exist in the database
        val nonExistentRental = Rental(Id(999999), Date("2023-10-10"), Duration(10, 12), user, court)
        
        // Attempt to delete a non-existent rental should fail
        assertFails { rentals.deleteRental(nonExistentRental) }

         */
    }
    
    @Test
    override fun updateRentalSuccessfully() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        val rental = rentals.createRental(
            court = court,
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = user
        )
        
        assertNotNull(rental)
        
        // Update the rental
        val updatedRental = rentals.updateRental(
            date = Date("2023-10-11"),
            duration = Duration(14, 16),
            rental = rental!!
        )
        
        assertNotNull(updatedRental)
        assertEquals(rental.rid, updatedRental?.rid)
        assertEquals("2023-10-11", updatedRental?.date?.value)
        assertEquals(14, updatedRental?.duration?.initDuration)
        assertEquals(16, updatedRental?.duration?.endDuration)
        assertEquals(court.id, updatedRental?.court?.id)
        assertEquals(user.uid, updatedRental?.user?.uid)

         */
    }
    
    @Test
    override fun updateRentalFailed() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court = createTestCourt(club = club)
        
        // Create a rental
        val rental = rentals.createRental(
            court = court,
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = user
        )
        
        assertNotNull(rental)
        
        // Invalid date
        assertFails { 
            rentals.updateRental(
                date = Date("invalid-date"),
                duration = Duration(14, 16),
                rental = rental!!
            ) 
        }
        
        // Invalid duration (end before start)
        assertFails { 
            rentals.updateRental(
                date = Date("2023-10-11"),
                duration = Duration(16, 14),
                rental = rental!!
            ) 
        }
        
        // Non-existent rental
        val nonExistentRental = Rental(Id(999999), Date("2023-10-10"), Duration(10, 12), user, court)
        assertFails { 
            rentals.updateRental(
                date = Date("2023-10-11"),
                duration = Duration(14, 16),
                rental = nonExistentRental
            ) 
        }

         */
    }
    
    @Test
    override fun getRentalsWithDateSuccessfully() {
        /*
        val user = createTestUser()
        val club = createTestClub(user = user)
        val court1 = createTestCourt("Court 1", club)
        val court2 = createTestCourt("Court 2", club)
        
        val rental1 = rentals.createRental(
            court = court1,
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = user
        )
        
        val rental2 = rentals.createRental(
            court = court2,
            date = Date("2023-10-10"),
            duration = Duration(14, 16),
            user = user
        )
        
        val rental3 = rentals.createRental(
            court = court1,
            date = Date("2023-10-11"),
            duration = Duration(9, 11),
            user = user
        )
        
        assertNotNull(rental1)
        assertNotNull(rental2)
        assertNotNull(rental3)
        
        val rentalsForDate = rentals.getRentalsWithDate(Date("2023-10-10"))
        
        assertNotNull(rentalsForDate)
        assertEquals(2, rentalsForDate.size)
        assertTrue(rentalsForDate.any { it.court.id == court1.id && it.duration.initDuration == 10 })
        assertTrue(rentalsForDate.any { it.court.id == court2.id && it.duration.initDuration == 14 })

         */
    }
    
    @Test
    override fun getRentalsWithDateFailed() {
        /*
        // No rentals for this date
        val rentalsForDate = rentals.getRentalsWithDate(Date("2023-12-25"))
        
        assertTrue(rentalsForDate.isEmpty())
        
        // Invalid date
        assertFails { rentals.getRentalsWithDate(Date("invalid-date")) }

         */
    }
}