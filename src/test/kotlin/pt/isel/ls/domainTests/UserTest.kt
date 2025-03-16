package pt.isel.ls.domainTests

import pt.isel.ls.domain.*
import kotlin.test.*

class UserTest {
    @Test
    fun `User with valid parameters`() {
        val user = User(
            uid = Id(1),
            name = Name("tubarao"),
            email = Email("luka.roca@gmail.com")
        )
        assertEquals(1, user.uid.id)
        assertEquals("tubarao", user.name.name)
        assertEquals("luka.roca@gmail.com", user.email.value)
    }

    @Test
    fun `Throw exception if user name is blank`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            User(
                uid = Id(1),
                name = Name(""),
                email = Email("luka@gmail.com")
            )
        }
        assertEquals("User must have a name", exception.message)
    }

    @Test
    fun `Email should contain @`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Email("invalid.com")
        }
        assertEquals("Email must have @ in it", exception.message)
    }
}
