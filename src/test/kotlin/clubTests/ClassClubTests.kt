package clubTests

import org.junit.Test
import pt.isel.ls.domain.*
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ClassClubTest {

    @Test
    fun `Club Valid`() {
        val club = Club(Id(1), Name("Padel Club"),
            Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"),Token(UUID.randomUUID().toString()), Password("securePassword") )))
        assertEquals(1,club.id.id)
        assertEquals("Padel Club", club.name.name)
        assertEquals(10, club.owner.user.uid.id)
    }

    @Test
    fun `Club with No Name`() {
        assertFailsWith<IllegalArgumentException> {
            Club(
                Id(2),
                Name(""),
                Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"),Token(UUID.randomUUID().toString()), Password("securePassword") ))
            )
        }
    }

    @Test
    fun `Club with Short Name`() {
        assertFailsWith<IllegalArgumentException> {
            Club(
                Id(3),
                Name("PC"),
                Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"),Token(UUID.randomUUID().toString()), Password("securePassword") ))
            )
        }
    }

    @Test
    fun `Club with Long Name`() {
        assertFailsWith<IllegalArgumentException> {
            Club(
                Id(4),
                Name("P".repeat(101)),
                Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"),Token(UUID.randomUUID().toString()), Password("securePassword") ))
            )
        }
    }

    @Test
    fun `Club with Invalid Characters in Name`() {
        assertFailsWith<IllegalArgumentException> {
            Club(
                Id(5),
                Name("Padel@123"),
                Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"),Token(UUID.randomUUID().toString()), Password("securePassword") ))
            )
        }
    }

    @Test
    fun `Club with Invalid ID`() {
        assertFailsWith<IllegalArgumentException> {
            Club(
                Id(-1),
                Name("Invalid Club"),
                Owner(User(Id(10), Name("Michael"), Email("michael@gmail.com"),Token(UUID.randomUUID().toString()), Password("securePassword") ))
            )
        }
    }

    @Test
    fun `Club with Invalid Owner ID`() {
        assertFailsWith<IllegalArgumentException> {
            Club(
                Id(6),
                Name("Padel Masters"),
                Owner(User(Id(-5), Name("Michael"), Email("michael@gmail.com"),Token(UUID.randomUUID().toString()), Password("securePassword") ))
            )
        }
    }
}