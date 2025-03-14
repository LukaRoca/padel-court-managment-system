package pt.isel.ls.domain

data class Club(
    val id : Id,
    val name : Name,
    val owner : Owner,
) {
    init {
        require(name.name.isNotBlank()) { "Name must not be empty" }
    }
}


