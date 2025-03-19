package pt.isel.ls.domain

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Serializable
data class Name (val name : String)

@Serializable
data class Owner (val user : User)

@Serializable
data class Email(val value: String) {
    init {
        require(value.contains("@")) { "Email must have @ in it" }
    }
}

@Serializable
data class Token(val token : String)

@Serializable
data class Id (val id : Int){
    init {
        if(id <= 0)
            throw IllegalArgumentException("Id must be positive and non-zero")
    }
}


@Serializable
data class Duration (val hours : Int)

@Serializable
data class Date(
        val year: Int,
        val month: Int,
        val day: Int,
        val hour: Int = 0,
        val minute: Int = 0,
) {
    init {
        require(year >= 0) { "Year must be non-negative" }
        require(month in 1..12) { "Month must be between 1 and 12" }
        require(day in 1..31) { "Day must be between 1 and 31" }
        require(hour in 0..23) { "Hour must be between 0 and 23" }
        require(minute in 0..59) { "Minute must be between 0 and 59" }
    }

    companion object {
        fun fromStrings(dateStr: String, timeStr: String): Date {
            val dateParts = dateStr.split("-").map { it.toInt() }
            val timeParts = timeStr.split(":").map { it.toInt() }

            return Date(
                    year = dateParts[0],
                    month = dateParts[1],
                    day = dateParts[2],
                    hour = timeParts[0],
                    minute = timeParts[1]
            )
        }
    }
}
