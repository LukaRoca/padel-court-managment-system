package pt.isel.ls.domain

data class Name( val value : String)

data class Id( val value : Int){
    init {
        if(value <= 0)
            throw IllegalArgumentException("Invalid id")
    }
}
data class Email( val value : String){
    init {
        if(!value.contains("@"))
            throw IllegalArgumentException("Invalid email")
    }
}

data class User(
    val uid : Id,
    val name: Name,
    val email : Email,
)