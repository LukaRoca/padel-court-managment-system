package pt.isel.ls.domain

data class Court(
    val id : Id,
    val name : Name,
    val owner : Owner,
) {
    init {
        require(name.name.isNotBlank()) { "Name must not be empty" }
        require(owner.name.name.isNotBlank()) { "Owner must not be empty" }
    }
}


