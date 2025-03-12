package pt.isel.ls.domain


data class Name (val name : String)

data class Owner (val name : Name)

data class Email( val value : String){
    init {
        if(!value.contains("@"))
            throw IllegalArgumentException("Invalid email")
    }
}

data class Id (val id : Int){
    init {
        if(id <= 0)
            throw IllegalArgumentException("Id must be positive and non-zero")
    }
}