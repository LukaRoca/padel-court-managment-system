package userTests

import org.junit.Test
import pt.isel.ls.domain.*
import pt.isel.ls.utils.Email
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.Name
import pt.isel.ls.utils.Password
import pt.isel.ls.utils.Token
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ClassUserTest {
    @Test
    fun `User Valid`() {
        val user = User(
            Id(1),
            Name("Michael"),
            Email("michael@example.com"),
            Token(UUID.randomUUID().toString()),
            Password("securePassword")
        )
        assertEquals(1, user.uid.id)
        assertEquals("Michael", user.name.name)
        assertEquals("michael@example.com", user.email.value)
    }

    @Test
    fun `User with No Name`() {
        assertFailsWith<IllegalArgumentException> {User(
            Id(1),
            Name(""),
            Email("michael@example.com"),
            Token(UUID.randomUUID().toString()),
            Password("securePassword")
        )}
    }

    @Test
    fun `User with Invalid Email`() {
        assertFailsWith<IllegalArgumentException> {
            User(
                Id(2),
                Name("John Doe"),
                Email("invalid-email"),
                Token(UUID.randomUUID().toString()),
                Password("securePassword")
            )
        }
    }

    @Test
    fun `User with Empty Email`() {
        assertFailsWith<IllegalArgumentException> {
            User(
                Id(3),
                Name("Alice"),
                Email(""),
                Token(UUID.randomUUID().toString()),
                Password("securePassword")
            )
        }
    }

    @Test
    fun `User with Invalid ID`() {
        assertFailsWith<IllegalArgumentException> {
            User(
                Id(-1),
                Name("Bob"),
                Email("bob@example.com"),
                Token(UUID.randomUUID().toString()),
                Password("securePassword")
            )
        }
    }

    @Test
    fun `User with Invalid Token`() {
        assertFailsWith<IllegalArgumentException> {
            User(
                Id(4),
                Name("Charlie"),
                Email("charlie@example.com"),
                Token("short"),
                Password("securePassword")
            )
        }
    }

    @Test
    fun `User with Special Characters in Name`() {
        assertFailsWith<IllegalArgumentException> {
            User(
                Id(5),
                Name("John@123"),
                Email("john@example.com"),
                Token(UUID.randomUUID().toString()),
                Password("securePassword")
            )
        }
    }
}