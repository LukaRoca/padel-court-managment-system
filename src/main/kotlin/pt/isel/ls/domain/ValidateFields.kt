package pt.isel.ls.domain

fun validateName(name: String, minLength: Int, maxLength: Int, entity: String) {
    require(name.isNotBlank()) { "$entity name must not be empty" }
    require(name.length in minLength..maxLength) { "$entity name must be between $minLength and $maxLength characters" }
    require(name.all { it.isLetterOrDigit() || it.isWhitespace() }) { "$entity name must contain only letters, numbers, and spaces" }
}
fun validateClubName(name: String, minLength: Int, maxLength: Int, entity: String) {
    validateName(name, minLength, maxLength, entity)
}
