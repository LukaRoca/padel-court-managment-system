package pt.isel.ls.sql
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import kotlin.test.Test
import kotlin.test.assertTrue

class DataBaseTestsAfonso {
    private val url = "jdbc:postgresql://localhost:5432/postgres"
    private val user = "postgres"
    private val password = "tubarao"

    private fun connect(): Connection {
        return DriverManager.getConnection(url, user, password)
    }

    @Test
    fun testInsertStudent() {
        val connection = connect()
        val sql = "INSERT INTO students (number, name, course) VALUES (?, ?, ?)"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, 50484)
            stmt.setString(2, "Afonso")
            stmt.setInt(3, getCourseId("LEIC", connection))
            val rowsInserted = stmt.executeUpdate()
            assertTrue(rowsInserted > 0, "Falha ao inserir o aluno")
        }
        connection.close()
    }
    @Test
    fun testSelectStudents() {
        val connection = connect()
        val sql = "SELECT * FROM students"
        connection.prepareStatement(sql).use { stmt ->
            val rs: ResultSet = stmt.executeQuery()
            var found = false
            while (rs.next()) {
                if (rs.getInt("number") == 50484) {
                    found = true
                    break
                }
            }
            assertTrue(found, "Aluno não encontrado")
        }
        connection.close()
    }
    @Test
    fun testUpdateStudent() {
        val connection = connect()
        val sql = "UPDATE students SET name = ? WHERE number = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, "Paulo")
            stmt.setInt(2, 50484)
            val rowsUpdated = stmt.executeUpdate()
            assertTrue(rowsUpdated > 0, "Falha ao atualizar o aluno")
        }
        connection.close()
    }
    @Test
    fun testDeleteStudent() {
        val connection = connect()
        val sql = "DELETE FROM students WHERE number = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, 50484)
            val rowsDeleted = stmt.executeUpdate()
            assertTrue(rowsDeleted > 0, "Falha ao deletar o aluno")
        }
        connection.close()
    }
    private fun getCourseId(courseName: String, connection: Connection): Int {
        val sql = "SELECT cid FROM courses WHERE name = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, courseName)
            val rs: ResultSet = stmt.executeQuery()
            if (rs.next()) return rs.getInt("cid")
        }
        throw Exception("Curso não encontrado")
    }

}