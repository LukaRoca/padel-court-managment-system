package pt.isel.ls.domain

data class Id (val id : Int)
data class Name (val name : String)
data class Owner (val name : Name)

data class Court(
    val id : Id,
    val name : Name,
    val owner : Owner,
) {
    init {
        require(name.name.isNotBlank()) { "pt.isel.ls.domain.Name must not be empty" }
        require(owner.name.name.isNotBlank()) { "pt.isel.ls.domain.Owner must not be empty" }
    }
}