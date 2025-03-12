package pt.isel.ls.domainTests

import pt.isel.ls.domain.*
import kotlin.test.*

class UserTest {
    @Test
    fun `User with valid parameters`() {
        val user = User(
            id = Uid(1),
            user = UserName("tubarao"),
            email = Email("luka.roca@gmail.com")
        )
        assertEquals(1, user.id.id)
        assertEquals("tubarao", user.user.name)
        assertEquals("luka.roca@gmail.com", user.email.email)
    }

    @Test
    fun `Throw exception if user name is blank`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            User(
                id = Uid(1),
                user = UserName(""),
                email = Email("luka@gmail.com")
            )
        }
        assertEquals("User must have a name", exception.message)
    }

    @Test
    fun `Email should contain @`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Email("lukaerrado.com")
        }
        assertEquals("Email must have @ in it", exception.message)
    }
}
