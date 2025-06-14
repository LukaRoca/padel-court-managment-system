package data.users

import data.DataMemTests
import jdk.internal.org.jline.utils.InfoCmp
import junit.framework.TestCase.assertNull
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
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class UsersMemTests : DataMemTests(), UsersTest {
    @Test
    override fun createUsersSuccessfully() {
        val usr1 = User(
            Id(1),
            Name("Jaco"),
            Email("bjato@gmail.com"),
            Token("a8d9dce3-7519-4103-a65b-bc43a7e9022d"),
            Password("Tubarao3"),
        )

        val user1 = users.createUser(
            Name("Jaco"),
            Email("bjato@gmail.com"),
            Password("Tubarao3")
        )

        val usr2 = User(
            Id(2),
            Name("Afonso"),
            Email("fonfon@gmail.com"),
            Token("a8d9dce3-7519-4103-a65b-bc43a7e9022a"),
            Password("Salmao1234"),
        )

        val user2 = users.createUser(
            Name("Afonso"),
            Email("fonfon@gmail.com"),
            Password("Salmao1234")
        )

        val usr3 = User(
            Id(3),
            Name("Luka"),
            Email("lukako@gmail.com"),
            Token("a8d9dce3-7519-4103-a65b-bc43a7e9022b"),
            Password("Tartaruga45"),
        )

        val user3 = users.createUser(
            Name("Luka"),
            Email("lukako@gmail.com"),
            Password("Tartaruga45")
        )

        assertEquals(usr1.name,user1.name)
        assertEquals(usr1.email, user1.email)
        assertEquals(user1.password, user1.password)

        assertEquals(usr2.name, user2.name)
        assertEquals(usr2.email, user2.email)
        assertEquals(user2.password, user2.password)

        assertEquals(usr3.name, user3.name)
        assertEquals(usr3.email, user3.email)
        assertEquals(usr3.password, user3.password)
    }

    @Test
    override fun createUsersFailed() {
        // First with Email not Valid
        assertFails { users.createUser(Name("Jaco"), Email(""), Password("Tubaorao3")) }

        // Second with Password not Valid
        assertFails { users.createUser(Name("Jaco"), Email("bjato@gmail.com"), Password("Tuba")) }
    }
    @Test
    override fun getUsersByIdSuccessfully() {
        val usr1 = users.createUser(
            Name("Jaco"),
            Email("bjato@gmail.com"),
            Password("Tubarao3")
        )

        val user1 = users.getUserById(usr1.uid)

        val usr2 = users.createUser(
            Name("Afonso"),
            Email("fonfon@gmail.com"),
            Password("Salmao1234")
        )

        val user2 = users.getUserById(usr2.uid)

        val usr3 = users.createUser(
            Name("Luka"),
            Email("lukako@gmail.com"),
            Password("Tartaruga45")
        )

        val user3 = users.getUserById(usr3.uid)

        assertEquals(usr1, user1)
        assertEquals(usr2, user2)
        assertEquals(usr3, user3)
    }

    @Test
    override fun getUsersByIdFailed() {
        // User with this Id doesn't exist
        assertNull(users.getUserById(Id(929989819)))

        // Invalid ID
        assertFails {users.getUserById(Id(-4))}
    }

    @Test
    override fun getUsersByTokenSuccessfully() {
        val usr1 = users.createUser(
            Name("Jaco"),
            Email("bjato@gmail.com"),
            Password("Tubarao3")
        )

        val user1 = users.getUserByToken(usr1.token)

        val usr2 = users.createUser(
            Name("Afonso"),
            Email("fonfon@gmail.com"),
            Password("Salmao1234")
        )

        val user2 = users.getUserByToken(usr2.token)

        val usr3 = users.createUser(
            Name("Luka"),
            Email("lukako@gmail.com"),
            Password("Tartaruga45")
        )

        val user3 = users.getUserByToken(usr3.token)

        assertEquals(usr1, user1)
        assertEquals(usr2, user2)
        assertEquals(usr3, user3)
    }
    @Test
    override fun getUsersByTokenFailed() {
        // User with this Token doesn't exist
        assertNull(users.getUserByToken(Token("60c021f1-4231-4c55-bcdc-2ccf126c7427")))

        // Invalid Token
        assertFails {users.getUserByToken(Token("60c021f1-4231")) }
    }
    @Test
    override fun getAllUsersSuccessfully() {
        val usr1 = users.createUser(
            Name("Jaco"),
            Email("bjato@gmail.com"),
            Password("Tubarao3")
        )

        val usr2 = users.createUser(
            Name("Afonso"),
            Email("fonfon@gmail.com"),
            Password("Salmao1234")
        )

        val usr3 = users.createUser(
            Name("Luka"),
            Email("lukako@gmail.com"),
            Password("Tartaruga45")
        )

        val users = users.getAllUsers()

        assertEquals(users.size, 3)
    }
    @Test
    override fun getAllUsersFailed() {
        val usr1 = users.createUser(
            Name("Jaco"),
            Email("bjato@gmail.com"),
            Password("Tubarao3")
        )

        val usr2 = users.createUser(
            Name("Afonso"),
            Email("fonfon@gmail.com"),
            Password("Salmao1234")
        )

        val usr3 = users.createUser(
            Name("Luka"),
            Email("lukako@gmail.com"),
            Password("Tartaruga45")
        )

        val users = users.getAllUsers()

        assertNotEquals(users.size, 2)
    }
}