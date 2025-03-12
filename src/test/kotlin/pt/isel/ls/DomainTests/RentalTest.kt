package pt.isel.ls.DomainTests

import org.junit.Test
import pt.isel.ls.domain.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RentalTest {

    @Test
    fun `Rental is correct`(){
        val rental = Rental(Number(2),
            Date(2004, 4, 5),
            Duration(2),
            User(Id(10), UserName("Jaco"), Email("bjaco@gmail.com")),
            Court(Id(1), Name("Luz"), Owner(Name("Luis")) ))
        assertEquals(2, rental.rid.rid)
        assertEquals(2004, rental.date.year)
        assertEquals(2, rental.duration.hours)
        assertEquals(10, rental.user.id.id)
        assertEquals("Luz", rental.court.name.name)
    }

    @Test
    fun `Rental Duration not Valid`(){
        assertFailsWith<IllegalArgumentException> {
            Rental(Number(2),
                Date(2004, 4, 5),
                Duration(-4),
                User(Id(10), UserName("Jaco"), Email("bjaco@gmail.com")),
                Court(Id(1), Name("Luz"), Owner(Name("Luis")) ))
        }
    }

    @Test
    fun `Rental Date not Valid`(){
        assertFailsWith<IllegalArgumentException> {
            Rental(Number(2),
                Date(0, 0, 0),
                Duration(4),
                User(Id(10), UserName("Jaco"), Email("bjaco@gmail.com")),
                Court(Id(1), Name("Luz"), Owner(Name("Luis")) ))
        }
    }

    @Test
    fun `Court name not Valid`(){
        assertFailsWith<IllegalArgumentException> {
            Rental(Number(2),
                Date(0, 0, 0),
                Duration(4),
                User(Id(10), UserName("Jaco"), Email("bjaco@gmail.com")),
                Court(Id(1), Name(""), Owner(Name("Luis")) ))
        }
    }
}