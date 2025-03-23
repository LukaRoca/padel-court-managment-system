/*package pt.isel.ls.domainTests

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
                id = Id(1),
                owner = Owner(User(Id(1), Name("Luka Roca"), Email("123@gmail.com")))
            )
        )

        assertEquals(1, court.id.id)
        assertEquals("Tubarao", court.name.name)
        assertEquals("PadelClub", court.club.name.name)
        assertEquals("Luka Roca", court.club.owner.user.name.name)
    }

    @Test
    fun `Throw exception if name is empty`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Court(
                id = Id(1),
                name = Name(""),
                club = Club(
                    name = Name("PadelClub"),
                    id = Id(1),
                    owner = Owner(User(Id(1), Name("Luka Roca"), Email("123@gmail.com")))
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
                    id = Id(1),
                    owner = Owner(User(Id(1), Name("Luka Roca"), Email("123@gmail.com")))
                )
            )
        }
        assertEquals("Club name must not be empty", exception.message)
    }
}
*/