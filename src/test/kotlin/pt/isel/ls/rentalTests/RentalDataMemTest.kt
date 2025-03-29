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
    fun `Get Available Hours`() {
        val token = Token(UUID.randomUUID().toString())
        RentalDataMem.createRental(Id(1), Id(1), Date("2023-10-10"), Duration(10, 20), token)
        val availableHours = RentalDataMem.getAvailableHours(Id(1), Id(1), Date("2023-10-10"))
        assertNotNull(availableHours)
        assertEquals((7..9).toList() + (20..21).toList(), availableHours)
    }
}