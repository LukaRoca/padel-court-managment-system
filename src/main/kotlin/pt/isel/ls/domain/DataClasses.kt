package pt.isel.ls.domain

import kotlinx.serialization.Serializable

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
data class Duration (val initDuration : Int, val endDuration : Int) {
    val hours = endDuration - initDuration
    init {
        require(hours > 0) { "Duration must be positive" }
    }
}

@Serializable
data class Date( val value: String)



