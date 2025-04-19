package pt.isel.ls.domain

data class Name (val name : String)

data class Owner (val user : User)

data class Email(val value: String) {
    init {
        require(value.contains("@")) { "Email must have @ in it" }
    }
}

data class Token(val token : String)

data class Id (val id : Int){
    init {
        if(id <= 0)
            throw IllegalArgumentException("Id must be positive and non-zero")
    }
}

data class Duration (val initDuration : Int, val endDuration : Int) {
    val hours = endDuration - initDuration
    init {
        require(hours > 0) { "Duration must be positive" }
    }
}

data class Date( val value: String)