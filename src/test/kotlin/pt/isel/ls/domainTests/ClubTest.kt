package pt.isel.ls.domainTests

import pt.isel.ls.domain.*
import kotlin.test.*

class ClubTest {

    @Test
    fun `club creation with valid name`() {
        val name = Name("PadelClub")
        val owner = Owner(Name("Luka Roca"))
        val club = Club(1, name, owner)

        assertEquals(1, club.id)
        assertEquals("PadelClub", club.name.name)
        assertEquals("Luka Roca", club.owner.name.name)
    }

    @Test
    fun `club creation with empty name should fail`() {
        val name = Name("")
        val owner = Owner(Name("Luka Roca"))

        assertFailsWith<IllegalArgumentException> {
            Club(1, name, owner)
        }
    }
}