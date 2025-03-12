package pt.isel.ls.domainTests

import pt.isel.ls.domain.*
import kotlin.test.*

class CourtTest {

    @Test
    fun `Court with valid parameters`() {
        val court = Court(
            id = Id(1),
            name = Name("Tubarao"),
            club = Club(
                name = Name("PadelClub"),
                id = 1,
                owner = Owner(Name("Luka Roca"))
            )
        )

        assertEquals(1, court.id.id)
        assertEquals("Tubarao", court.name.name)
        assertEquals("PadelClub", court.club.name.name)
        assertEquals("Luka Roca", court.club.owner.name.name)
    }

    @Test
    fun `Throw exception if name is empty`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Court(
                id = Id(1),
                name = Name(""),
                club = Club(
                    name = Name("PadelClub"),
                    id = 1,
                    owner = Owner(Name("Luka Roca"))
                )
            )
        }
        assertEquals("Name must not be empty", exception.message)
    }

    @Test
    fun `Throw exception if club name is empty`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Court(
                id = Id(1),
                name = Name("Luka Roca"),
                club = Club(
                    name = Name(""),
                    id = 1,
                    owner = Owner(Name("Luka Roca"))
                )
            )
        }
        assertEquals("Club name must not be empty", exception.message)
    }
}
