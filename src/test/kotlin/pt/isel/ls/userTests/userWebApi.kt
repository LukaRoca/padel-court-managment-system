package pt.isel.ls.userTests

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
    //val client = okHttp()

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
        assertEquals(storage.getUsers(), Json.decodeFromString<User>(req.bodyString()))
    }
}
