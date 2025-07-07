package data.clubs
import pt.isel.ls.data.Data
import kotlin.test.Test
interface ClubsDBTests {
    val db: Data

    @Test
    fun `create and retrieve club`()

    @Test
    fun `getClub returns null for non-existent id`()

    @Test
    fun `getClubs returns all clubs`()

    @Test
    fun `add and remove court to club`()

    @Test
    fun `searchByName returns clubs containing fragment`()
}
