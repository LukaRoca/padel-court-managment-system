package courtsTests

import org.junit.Test
import pt.isel.ls.domain.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import java.util.UUID

class ClassCourtTest {

    @Test
    fun `Court Valid`() {
        val club = Club(Id(1), Name("Padel Club"), Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"), Token(UUID.randomUUID().toString()), Password("securePassword"))))
        val court = Court(Id(1), Name("Padel Court 1"), club)
        assertEquals(1, court.id.id)
        assertEquals("Padel Court 1", court.name.name)
        assertEquals("Padel Club", court.club.name.name)
    }

    @Test
    fun `Court with No Name`() {
        val club = Club(Id(1), Name("Padel Club"), Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"), Token(UUID.randomUUID().toString()), Password("securePassword"))))
        assertFailsWith<IllegalArgumentException> {
            Court(Id(2), Name(""), club)
        }
    }

    @Test
    fun `Court with Invalid ID`() {
        val club = Club(Id(1), Name("Padel Club"), Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"), Token(UUID.randomUUID().toString()), Password("securePassword"))))
         // Assuming Id should not be negative
         // If your implementation allows negative IDs, you can adjust this test accordingly
         // Here we assume that negative IDs are invalid
        assertFailsWith<IllegalArgumentException> {
            Court(Id(-1), Name("Padel Court 3"), club)
        }
    }

    @Test
    fun `Court with Short Name`() {
        val club = Club(Id(1), Name("Padel Club"), Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"), Token(UUID.randomUUID().toString()), Password("securePassword"))))
        assertFailsWith<IllegalArgumentException> {
            Court(Id(3), Name("PC"), club)
        }
    }

    @Test
    fun `Court with Long Name`() {
        val club = Club(Id(1), Name("Padel Club"), Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"), Token(UUID.randomUUID().toString()), Password("securePassword"))))
         // Assuming the maximum length for a court name is 100 characters
         // Adjust this value according to your actual implementation
        assertFailsWith<IllegalArgumentException> {
            Court(Id(4), Name("P".repeat(101)), club)
        }
    }

    @Test
    fun `Court with Invalid Characters in Name`() {
        val club = Club(Id(1), Name("Padel Club"), Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"), Token(UUID.randomUUID().toString()), Password("securePassword"))))
         // Assuming that court names should not contain special characters like '@'
         // Adjust this according to your actual validation rules
        assertFailsWith<IllegalArgumentException> {
            Court(Id(5), Name("Padel@123"), club)
        }
    }
}