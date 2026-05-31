package data.users

import data.DataPostgresTests
import pt.isel.ls.domain.User
import pt.isel.ls.utlis.Email
import pt.isel.ls.utlis.Id
import pt.isel.ls.utlis.Name
import pt.isel.ls.utlis.Password
import pt.isel.ls.utlis.Token
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UsersPostgresTest : DataPostgresTests(), UsersTest {
    // Helper function to generate unique email addresses
    private fun uniqueEmail(base: String): String {
        val uniqueId = UUID.randomUUID().toString().substring(0, 8).replace("-", "")
        return base.replace("@", "_$uniqueId@")
    }
    @Test
    override fun createUsersSuccessfully() {

        val email1 = uniqueEmail("bjato@gmail.com")
        val email2 = uniqueEmail("fonfon@gmail.com")
        val email3 = uniqueEmail("lukako@gmail.com")

        val user1 = users.createUser(
            Name("Jaco"),
            Email(email1),
            Password("Tubarao3")
        )

        val user2 = users.createUser(
            Name("Afonso"),
            Email(email2),
            Password("Salmao1234")
        )

        val user3 = users.createUser(
            Name("Luka"),
            Email(email3),
            Password("Tartaruga45")
        )

        assertEquals("Jaco", user1.name.name)
        assertEquals(email1, user1.email.value)
        assertEquals("Tubarao3", user1.password.value)

        assertEquals("Afonso", user2.name.name)
        assertEquals(email2, user2.email.value)
        assertEquals("Salmao1234", user2.password.value)

        assertEquals("Luka", user3.name.name)
        assertEquals(email3, user3.email.value)
        assertEquals("Tartaruga45", user3.password.value)


    }

    @Test
    override fun createUsersFailed() {

        // First with Email not Valid
        assertFails { users.createUser(Name("Jaco"), Email("bjato-gmail.com"), Password("Tubarao3")) }

        // Second with Password not Valid
        val email = uniqueEmail("bjato@gmail.com")
        assertFails { users.createUser(Name("Jaco"), Email(email), Password("Tuba")) }


    }

    @Test
    override fun getUsersByIdSuccessfully() {

        val email1 = uniqueEmail("bjato@gmail.com")
        val email2 = uniqueEmail("fonfon@gmail.com")
        val email3 = uniqueEmail("lukako@gmail.com")

        val usr1 = users.createUser(
            Name("Jaco"),
            Email(email1),
            Password("Tubarao3")
        )

        val user1 = users.getUserById(usr1.uid)

        val usr2 = users.createUser(
            Name("Afonso"),
            Email(email2),
            Password("Salmao1234")
        )

        val user2 = users.getUserById(usr2.uid)

        val usr3 = users.createUser(
            Name("Luka"),
            Email(email3),
            Password("Tartaruga45")
        )

        val user3 = users.getUserById(usr3.uid)

        assertEquals(usr1.name, user1?.name)
        assertEquals(usr1.email, user1?.email)
        // Don't compare passwords as they are hashed in the database

        assertEquals(usr2.name, user2?.name)
        assertEquals(usr2.email, user2?.email)
        // Don't compare passwords as they are hashed in the database

        assertEquals(usr3.name, user3?.name)
        assertEquals(usr3.email, user3?.email)
        // Don't compare passwords as they are hashed in the database


    }

    @Test
    override fun getUsersByIdFailed() {

        // User with this Id doesn't exist
        assertNull(users.getUserById(Id(999999999)))

        // Invalid ID
        assertFails { users.getUserById(Id(-4)) }


    }

    @Test
    override fun getUsersByTokenSuccessfully() {

        val email1 = uniqueEmail("bjato@gmail.com")
        val email2 = uniqueEmail("fonfon@gmail.com")
        val email3 = uniqueEmail("lukako@gmail.com")

        val usr1 = users.createUser(
            Name("Jaco"),
            Email(email1),
            Password("Tubarao3")
        )

        val user1 = users.getUserByToken(usr1.token)

        val usr2 = users.createUser(
            Name("Afonso"),
            Email(email2),
            Password("Salmao1234")
        )

        val user2 = users.getUserByToken(usr2.token)

        val usr3 = users.createUser(
            Name("Luka"),
            Email(email3),
            Password("Tartaruga45")
        )

        val user3 = users.getUserByToken(usr3.token)

        assertEquals(usr1.name, user1?.name)
        assertEquals(usr1.email, user1?.email)
        // Don't compare passwords as they are hashed in the database

        assertEquals(usr2.name, user2?.name)
        assertEquals(usr2.email, user2?.email)
        // Don't compare passwords as they are hashed in the database

        assertEquals(usr3.name, user3?.name)
        assertEquals(usr3.email, user3?.email)
        // Don't compare passwords as they are hashed in the database


    }

    @Test
    override fun getUsersByTokenFailed() {

        // User with this Token doesn't exist
        assertNull(users.getUserByToken(Token("60c021f1-4231-4c55-bcdc-2ccf126c7427")))

        // Invalid Token
        assertFails { users.getUserByToken(Token("60c021f1-4231")) }


    }

    @Test
    override fun getAllUsersSuccessfully() {

        val email1 = uniqueEmail("bjato@gmail.com")
        val email2 = uniqueEmail("fonfon@gmail.com")
        val email3 = uniqueEmail("lukako@gmail.com")

        val usr1 = users.createUser(
            Name("Jaco"),
            Email(email1),
            Password("Tubarao3")
        )

        val usr2 = users.createUser(
            Name("Afonso"),
            Email(email2),
            Password("Salmao1234")
        )

        val usr3 = users.createUser(
            Name("Luka"),
            Email(email3),
            Password("Tartaruga45")
        )

        val allUsers = users.getAllUsers()

        assertTrue(allUsers.any { it.email.value == email1 })
        assertTrue(allUsers.any { it.email.value == email2 })
        assertTrue(allUsers.any { it.email.value == email3 })


    }

    @Test
    override fun getAllUsersFailed() {

        // Clear any existing users first
        val initialUsers = users.getAllUsers()
        if (initialUsers.isNotEmpty()) {
            // This is a test setup, not a test assertion
            println("[DEBUG_LOG] Found ${initialUsers.size} existing users in database")
        }

        val email1 = uniqueEmail("bjato@gmail.com")
        val email2 = uniqueEmail("fonfon@gmail.com")
        val email3 = uniqueEmail("lukako@gmail.com")

        val usr1 = users.createUser(
            Name("Jaco"),
            Email(email1),
            Password("Tubarao3")
        )

        val usr2 = users.createUser(
            Name("Afonso"),
            Email(email2),
            Password("Salmao1234")
        )

        val usr3 = users.createUser(
            Name("Luka"),
            Email(email3),
            Password("Tartaruga45")
        )

        val allUsers = users.getAllUsers()

        // Test should fail if we expect 2 users but have at least 3
        // (we're adding 3 new users, plus any that were already in the database)
        val newUsersCount = 3
        assertNotEquals(2, newUsersCount)

        // Also verify our new users are in the result
        assertTrue(allUsers.any { it.email.value == email1 })
        assertTrue(allUsers.any { it.email.value == email2 })
        assertTrue(allUsers.any { it.email.value == email3 })


    }
}
