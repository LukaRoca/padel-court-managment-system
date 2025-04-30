package pt.isel.ls.domain
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class Name (val name : String)

data class Owner (val user : User)

data class Email(val value: String) {
    init { require(value.isNotBlank()) { "Email must not be empty" }
        require(value.length in 5..100) { "Email must be between 5 and 100 characters" }
        require(value.all { it.isLetterOrDigit() || it == '@' || it == '.' || it == '_' }) { "Email must contain only letters, numbers, @, ., and _" } }
}

data class Token(val token : String) {
    init{
        require(token.length >= 20) { "Token must be at least 20 characters long" }
        require(token.isNotBlank()) { "Token must not be empty" }
    }
}

data class Id (val id : Int){
    init { if(id <= 0) throw IllegalArgumentException("Id must be positive and non-zero") }
}

data class Duration (val initDuration : Int, val endDuration : Int) {
    val hours = endDuration - initDuration

    init {
        require(hours > 0) { "Duration must be positive" }
        require(initDuration in 0..23) { "Initial duration must be between 0 and 23" }
        require(endDuration in 1..24) { "End duration must be between 1 and 24" }
        require(endDuration > initDuration) { "End duration must be greater than initial duration" }
    }
}
data class Date( val value: String) {
    init {
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            LocalDate.parse(value, formatter)
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException("Date must be in format YYYY-MM-DD and be a valid date")
        }
    }
}