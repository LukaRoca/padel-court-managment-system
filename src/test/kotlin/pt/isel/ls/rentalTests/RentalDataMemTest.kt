package pt.isel.ls.rentalTests
import org.junit.Test
import pt.isel.ls.domain.*
import pt.isel.ls.domain.Date
import pt.isel.ls.storage.dataMem.RentalDataMem
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class RentalDataMemTest {

    @Test
    fun `Create Rental Invalid Court`() {
        val token = Token(UUID.randomUUID().toString())
        val rental = RentalDataMem.createRental(Id(1), Id(999), Date("2023-10-10"), Duration(10, 20), token)
        assertNull(rental)
    }

    @Test
    fun `Create Rental Invalid Club`() {
        val token = Token(UUID.randomUUID().toString())
        val rental = RentalDataMem.createRental(Id(999), Id(1), Date("2023-10-10"), Duration(10, 20), token)
        assertNull(rental)
    }

    @Test
    fun `Get Rental By ID`() {
        val token = Token(UUID.randomUUID().toString())
        val rental = RentalDataMem.createRental(Id(1), Id(1), Date("2023-10-10"), Duration(10, 20), token)
        val retrievedRental = RentalDataMem.getRentalById(rental!!.rid)
        assertNotNull(retrievedRental)
        assertEquals(rental, retrievedRental)
    }
    @Test
    fun `Create Multiple Rentals for Same Court`() {
        val token = Token(UUID.randomUUID().toString())
        val rental1 = RentalDataMem.createRental(Id(1), Id(1), Date("2023-10-10"), Duration(10, 12), token)
        val rental2 = RentalDataMem.createRental(Id(1), Id(1), Date("2023-10-11"), Duration(14, 16), token)
        assertNotNull(rental1)
        assertNotNull(rental2)
        assertEquals(Id(1), rental1.court.id)
        assertEquals(Id(1), rental2.court.id)
        assertEquals(Date("2023-10-10"), rental1.date)
        assertEquals(Date("2023-10-11"), rental2.date)
    }
}