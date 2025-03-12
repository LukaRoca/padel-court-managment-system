package pt.isel.ls.domainTests

import pt.isel.ls.domain.*
import kotlin.test.*

class CourtTest {
    @Test
    fun `Court with valid parameters`() {
        val court = Court(
            id = Id(1),
            name = Name("tubarao"),
            owner = Owner(Name("Luka Roca"))
        )
        assertEquals(1, court.id.id)
        assertEquals("tubarao", court.name.name)
        assertEquals("Luka Roca", court.owner.name.name)
    }
    @Test
    fun `Throw exception if name is empty`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Court(
                id = Id(1),
                name = Name(""),
                owner = Owner(Name("Luka Roca"))
            )
        }
        assertEquals("pt.isel.ls.domain.Name must not be empty", exception.message)
    }
    @Test
    fun `Throw exception if owner name is empty`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Court(
                id = Id(1),
                name = Name("Luka Roca"),
                owner = Owner(Name(""))
            )
        }
        assertEquals("pt.isel.ls.domain.Owner must not be empty", exception.message)
    }
}

