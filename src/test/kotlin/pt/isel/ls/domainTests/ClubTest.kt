/*package pt.isel.ls.domainTests

import pt.isel.ls.domain.*
import kotlin.test.*

class ClubTest {

    @Test
    fun `club creation with valid name`() {
        val name = Name("PadelClub")
        val owner = Owner(User(Id(1), Name("Luka Roca"), Email("123@gmail.com")))
        val club = Club(Id(1), name, owner)

        assertEquals(1, club.id.id)
        assertEquals("PadelClub", club.name.name)
        assertEquals("Luka Roca", club.owner.user.name.name)
    }

    @Test
    fun `club creation with empty name should fail`() {
        val name = Name("")
        val owner = Owner(User(Id(1), Name("Luka Roca"), Email("123@gmail.com")))

        assertFailsWith<IllegalArgumentException> {
            Club(Id(1), name, owner)
        }
    }
}*/