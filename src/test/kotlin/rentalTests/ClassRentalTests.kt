package rentalTests

import org.junit.Test
import pt.isel.ls.domain.*
import pt.isel.ls.domain.Date
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ClassRentalTest {

    @Test
    fun `Rental Valid`() {
        val user = User(Id(1), Name("Michael"), Email("michael@example.com"), Token(UUID.randomUUID().toString()), Password("securePassword"))
        val club = Club(Id(1), Name("Padel Club"), Owner(user))
        val court = Court(Id(1), Name("Padel Court 1"), club)
        val rental = Rental(Id(1), Date("2023-10-10"), Duration(10, 20), user, court)
        assertEquals(1, rental.rid.id)
        assertEquals("2023-10-10", rental.date.value)
        assertEquals(10, rental.duration.initDuration)
        assertEquals(20, rental.duration.endDuration)
        assertEquals(user, rental.user)
        assertEquals(court, rental.court)
    }

    @Test
    fun `Rental with Negative ID`() {
        val user = User(Id(1), Name("Michael"), Email("michael@example.com"), Token(UUID.randomUUID().toString()), Password("securePassword"))
        val club = Club(Id(1), Name("Padel Club"), Owner(user))
        val court = Court(Id(1), Name("Padel Court 1"), club)
        assertFailsWith<IllegalArgumentException> {
            Rental(Id(-1), Date("2023-10-10"), Duration(10, 20), user, court)
        }
    }

    @Test
    fun `Rental with Invalid Duration Range`() {
        val user = User(Id(1), Name("Michael"), Email("michael@example.com"), Token(UUID.randomUUID().toString()), Password("securePassword"))
        val club = Club(Id(1), Name("Padel Club"), Owner(user))
        val court = Court(Id(1), Name("Padel Court 1"), club)
        assertFailsWith<IllegalArgumentException> {
            Rental(Id(3), Date("2023-10-10"), Duration(25, 30), user, court)
        }
    }

    @Test
    fun `Rental with End Duration Less Than Start Duration`() {
        val user = User(Id(1), Name("Michael"), Email("michael@example.com"), Token(UUID.randomUUID().toString()), Password("securePassword"))
        val club = Club(Id(1), Name("Padel Club"), Owner(user))
        val court = Court(Id(1), Name("Padel Court 1"), club)
        assertFailsWith<IllegalArgumentException> {
            Rental(Id(4), Date("2023-10-10"), Duration(20, 10), user, court)
        }
    }
}