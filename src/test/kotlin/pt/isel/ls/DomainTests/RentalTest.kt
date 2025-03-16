package pt.isel.ls.DomainTests

import org.junit.Test
import pt.isel.ls.domain.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RentalTest {

    @Test
    fun `Rental is correct`(){
        val rental = Rental(Id(2),
            Date("2004-4-5"),
            Duration(2),
            User(Id(10), Name("Jaco"), Email("bjaco@gmail.com")),
            Court(Id(1), Name("Luz"), Club(Id(1), Name("Pontinha"), Owner(User(Id(1), Name("Luka Roca"), Email("123@gmail.com"))))))
        assertEquals(2, rental.rid.id)
        assertEquals(Date("2004-4-5"), rental.date)
        assertEquals(2, rental.duration.hours)
        assertEquals(Id(10), rental.user.uid)
        assertEquals("Luz", rental.court.name.name)
    }

    @Test
    fun `Rental Duration not Valid`(){
        assertFailsWith<IllegalArgumentException> {
            Rental(Id(2),
                Date("2004-04-05"),
                Duration(-4),
                User(Id(10), Name("Jaco"), Email("bjaco@gmail.com")),
                Court(Id(1), Name("Luz"), Club(Id(1), Name("Pontinha"), Owner(User(Id(1), Name("Luis"), Email("luis@gmail.com"))))))
        }
    }

    @Test
    fun `Rental Date not Valid`(){
        assertFailsWith<IllegalArgumentException> {
            Rental(Id(2),
                Date("0000-00-00"),
                Duration(4),
                User(Id(10), Name("Jaco"), Email("bjaco@gmail.com")),
                Court(Id(1), Name("Luz"), Club(Id(1), Name("Pontinha"), Owner(User(Id(1), Name("Luis"), Email("luis@gmail.com"))))))
        }
    }

    @Test
    fun `Court name not Valid`(){
        assertFailsWith<IllegalArgumentException> {
            Rental(Id(2),
                Date("0000-00-00"),
                Duration(4),
                User(Id(10), Name("Jaco"), Email("bjaco@gmail.com")),
                Court(Id(1), Name(""), Club(Id(1), Name("Pontinha"), Owner(User(Id(1), Name("Luis"), Email("luis@gmail.com"))))))
        }
    }
}