package pt.isel.ls.userTests

import kotlin.test.Test
import kotlin.test.assertEquals
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import org.http4k.core.Method.GET
import pt.isel.ls.webServices.UserServices

@Serializable
data class UserDTO(val name: String, val email: String)
@Serializable
data class UserResponse(val id: Int, val user: String, val email: String)
@Serializable
data class UserID(val id: Int)

class UserWebApiTests {

    private fun logRequest(request: Request) {
        println("Received request: ${request.bodyString()}")
    }

    private fun createUser(request: Request): Response {
        logRequest(request)
        val user = Json.decodeFromString<UserDTO>(request.bodyString())
        val createdUser = UserServices.createUser(user.name, user.email)
        val responseUser = mapOf(
            "name" to createdUser.user.name,
            "email" to createdUser.email.value
        )
        return Response(Status.CREATED)
            .header("content-type", "application/json")
            .body(Json.encodeToString(responseUser))
    }

    @Test
    fun `should create user successfully`() {
        val user = UserDTO("Luka", "luka.@gmail.com")
        val request = Request(POST, "/users").body(Json.encodeToString(user))
        val response = createUser(request)

        assertEquals(Status.CREATED, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertEquals(Json.encodeToString(user), response.bodyString())
    }

    private fun getUserByID(request: Request): Response {
        logRequest(request)
        val userId = Json.decodeFromString<UserID>(request.bodyString())
        val getUserByID = UserServices.getUserById(userId.id)
        val responseUser = getUserByID?.let {
            UserResponse(
                id = it.uid.id,
                user = it.user.name,
                email = it.email.value
            )
        }
        return if (responseUser != null) {
            Response(Status.OK)  // Resposta com status 200 (OK)
                .header("content-type", "application/json")
                .body(Json.encodeToString(responseUser))
        } else {
            Response(Status.NOT_FOUND)  // Caso o usuário não seja encontrado, retorna 404
                .header("content-type", "application/json")
                .body("{\"error\":\"User not found\"}")
        }
    }

    @Test
    fun `should get user by id successfully`() {
        val userId = UserID(1)
        val request = Request(GET, "/users").body(Json.encodeToString(userId))
        val response = getUserByID(request)
        val expectedUser = UserResponse(1, "Michael Jackson", "michael@gmail.com")

        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertEquals(Json.encodeToString(expectedUser), response.bodyString())
    }

    private fun getUsers(request: Request): Response {
        logRequest(request)
        val users = UserServices.getUsers()
        val userResponses = users.map {
            UserResponse(
                id = it.uid.id,
                user = it.user.name,
                email = it.email.value
            )
        }
        return Response(Status.OK)
            .header("content-type", "application/json")
            .body(Json.encodeToString(userResponses))
    }
/*
    @Test
    fun `should get all users`() {
        val users = listOf(
            UserResponse(1, "Michael Jackson", "michael@gmail.com"),
            UserResponse(2, "Luka Roca", "luka@gmail.com") // Adicione outro usuário para garantir que estamos lidando com uma lista
        )
        val response = getUsers(Request(GET, "/users"))

        assertEquals(Status.OK, response.status)
        assertEquals("application/json", response.header("content-type"))
        assertEquals(Json.encodeToString(users), response.bodyString())
    }
*/
}

/*
import kotlin.test.Test
import kotlinx.serialization.json.Json
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status
import pt.isel.ls.domain.User
import pt.isel.ls.storage.DataMem
import pt.isel.ls.storage.IStorage
import kotlin.test.assertEquals


class userWebApi {

    val storage: IStorage = DataMem
    val client = okHttp()

    @Test
    fun `get users`() {
        val req = client(Request(GET, "/users"))
        assertEquals(Status.OK, req.status)
        assertEquals(req.header("content-type"), "application/json")
        assertEquals(storage.getUsers(), Json.decodeFromString<List<User>>(req.bodyString()))
    }

    @Test
    fun `get user`() {
        val id = 1
        val req = client(Request(GET, "users"))
        assertEquals(Status.OK, req.status)
        assertEquals(req.header("content-type"), "application/json")
        assertEquals(storage.getUsers(), Json.decodeFromString<List<User>>(req.bodyString()))
    }

    @Test
    fun `get user by valid id`() {
        val userId = 1
        val req = client(Request(GET, "/users/$userId"))
        assertEquals(Status.OK, req.status)
        assertEquals("application/json", req.header("content-type"))
        val user = Json.decodeFromString<User>(req.bodyString())
        val expectedUser = storage.getUserById(userId)
        assertEquals(expectedUser, user)
    }

    @Test
    fun `get user by invalid id`() {
        val invalidUserId = 10
        val req = client(Request(GET, "/users/$invalidUserId"))
        assertEquals(Status.NOT_FOUND, req.status)
        val expectedError = """{"error":"User not found"}"""
        assertEquals(expectedError, response.bodyString())
    }
}
*/
