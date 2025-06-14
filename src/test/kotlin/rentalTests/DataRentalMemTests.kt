package rentalTests

import org.junit.Test
import pt.isel.ls.domain.*
import pt.isel.ls.utlis.Date
import pt.isel.ls.data.dataMem.RentalDataMem
import pt.isel.ls.utlis.Duration
import pt.isel.ls.utlis.Email
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.Owner
import pt.isel.ls.utlis.Password
import pt.isel.ls.utlis.Token
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class RentalDataMemTest {

    @Test
    fun `Create Rental with Valid Data`() {
        val token = Token(UUID.randomUUID().toString())
        val rental = RentalDataMem.createRental(
            court = Court(
                Id(1),
                Name("Padel Court 1"), Club(
                    Id(1),
                    Name("Padel Club"),
                    Owner(User(Id(1), Name("Michael"), Email("michael@gmail.com"), token, Password("securePassword")))
                )),
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = User(Id(1), Name("Michael"), Email("michael@gmail.com"), token, Password("securePassword"))
        )
        assertNotNull(rental)
        assertEquals("2023-10-10", rental.date.value)
        assertEquals(10, rental.duration.initDuration)
        assertEquals(12, rental.duration.endDuration)
    }

    @Test
    fun `Get Rental by ID`() {
        val token = Token(UUID.randomUUID().toString())
        val rental = RentalDataMem.createRental(
            court = Court(
                Id(1),
                Name("Padel Court 1"), Club(
                    Id(1),
                    Name("Padel Club"),
                    Owner(User(Id(1), Name("Michael"), Email("michael@gmail.com"), token, Password("securePassword")))
                )),
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = User(Id(1), Name("Michael"), Email("michael@gmail.com"), token, Password("securePassword"))
        )
        val retrievedRental = RentalDataMem.getRentalById(rental!!.rid)
        assertNotNull(retrievedRental)
        assertEquals(rental, retrievedRental)
    }

    @Test
    fun `Get Rental by Nonexistent ID`() {
        val rental = RentalDataMem.getRentalById(Id(999))
        assertNull(rental)
    }

    @Test
    fun `Create Multiple Rentals for Same Court`() {
        val token = Token(UUID.randomUUID().toString())
        val rental1 = RentalDataMem.createRental(
            court = Court(
                Id(1),
                Name("Padel Court 1"), Club(
                    Id(1),
                    Name("Padel Club"),
                    Owner(User(Id(1), Name("Michael"), Email("michael@gmail.com"), token, Password("securePassword")))
                )),
            date = Date("2023-10-10"),
            duration = Duration(10, 12),
            user = User(Id(1), Name("Michael"), Email("michael@gmail.com"), token, Password("securePassword"))
        )
        val rental2 = RentalDataMem.createRental(
            court = Court(
                Id(1),
                Name("Padel Court 1"), Club(
                    Id(1),
                    Name("Padel Club"),
                    Owner(User(Id(1), Name("Michael"), Email("michael@gmail.com"), token, Password("securePassword")))
                )),
            date = Date("2023-10-11"),
            duration = Duration(14, 16),
            user = User(Id(1), Name("Michael"), Email("michael@gmail.com"), token, Password("securePassword"))
        )
        assertNotNull(rental1)
        assertNotNull(rental2)
        assertEquals(Id(1), rental1.court.id)
        assertEquals(Id(1), rental2.court.id)
        assertEquals(Date("2023-10-10"), rental1.date)
        assertEquals(Date("2023-10-11"), rental2.date)
    }
}
