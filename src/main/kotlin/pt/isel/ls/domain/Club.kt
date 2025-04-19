package pt.isel.ls.domain

data class Club(
    val id : Id,
    val name : Name,
    val owner : Owner
) {
    init {
        require(name.name.isNotBlank()) { "Name must not be empty" }
        require(id.id > 0) { "Id must be greater than zero." }
        require(name.name.length in 3..100) { "Club name must be between 3 and 100 characters" }
        require(name.name.all { it.isLetterOrDigit() || it.isWhitespace() }) { "Club name must contain only letters, numbers, and spaces" }
        require(owner.user.uid.id > 0) { "Owner ID must be a valid positive number" }
    }
}


