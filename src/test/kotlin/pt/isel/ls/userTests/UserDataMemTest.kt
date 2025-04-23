package pt.isel.ls.userTests

import pt.isel.ls.domain.Email
import pt.isel.ls.domain.Id
import pt.isel.ls.domain.Name
import pt.isel.ls.domain.Token
import pt.isel.ls.storage.dataMem.UserDataMem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UserDataMemTest {

    @Test
    fun `test createUser adds user correctly`() {
        val user = UserDataMem.createUser(Name("John Doe"), Email("john.doe@example.com"))
        assertNotNull(user)
        assertEquals("John Doe", user.name.name)
        assertEquals("john.doe@example.com", user.email.value)
        assertNotNull(user.token)
    }

    @Test
    fun `test getUserById returns correct user`() {
        val user = UserDataMem.createUser(Name("Jane Doe"), Email("jane.doe@example.com"))
        val retrievedUser = UserDataMem.getUserById(user.uid)
        assertNotNull(retrievedUser)
        assertEquals(user.uid, retrievedUser.uid)
    }

    @Test
    fun `test getUserById returns null for non-existent user`() {
        val user = UserDataMem.getUserById(Id(999))
        assertNull(user)
    }

    @Test
    fun `test getUserByToken returns null for non-existent token`() {
        val user = UserDataMem.getUserByToken(Token("invalid-token"))
        assertNull(user)
    }
}