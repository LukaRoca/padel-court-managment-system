package data

import pt.isel.ls.data.dataPostgres.DataPostgres
import java.sql.DriverManager

private const val DEFAULT_TEST_DB_URL = "jdbc:postgresql://localhost/ls?user=postgres&password=tubarao"

private val TEST_DB_URL = System.getenv("TEST_DB_URL") ?: DEFAULT_TEST_DB_URL

abstract class DataPostgresTests : DataTests(DataPostgres(TEST_DB_URL)) {
	init {
		resetSchema()
	}

	private fun resetSchema() {
		DriverManager.getConnection(TEST_DB_URL).use { connection ->
			connection.createStatement().use { statement ->
				statement.execute("DROP TABLE IF EXISTS rental CASCADE")
				statement.execute("DROP TABLE IF EXISTS court CASCADE")
				statement.execute("DROP TABLE IF EXISTS club CASCADE")
				statement.execute("DROP TABLE IF EXISTS users CASCADE")

				statement.execute(
					"""
					CREATE TABLE users (
						uid SERIAL PRIMARY KEY,
						token VARCHAR(255) UNIQUE NOT NULL,
						name VARCHAR(255) NOT NULL,
						email VARCHAR(255) UNIQUE NOT NULL,
						password VARCHAR(255) NOT NULL
					)
					""".trimIndent()
				)

				statement.execute(
					"""
					CREATE TABLE club (
						cid SERIAL PRIMARY KEY,
						name VARCHAR(255) UNIQUE NOT NULL,
						owner INT REFERENCES users(uid)
					)
					""".trimIndent()
				)

				statement.execute(
					"""
					CREATE TABLE court (
						crid SERIAL PRIMARY KEY,
						name VARCHAR(255) NOT NULL,
						club INT REFERENCES club(cid) ON DELETE CASCADE
					)
					""".trimIndent()
				)

				statement.execute(
					"""
					CREATE TABLE rental (
						rid SERIAL PRIMARY KEY,
						date VARCHAR(255) NOT NULL,
						initDuration INT NOT NULL,
						endDuration INT NOT NULL,
						usr INT REFERENCES users(uid),
						court INT REFERENCES court(crid) ON DELETE CASCADE
					)
					""".trimIndent()
				)
			}
		}
	}
}
