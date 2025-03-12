package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class Name (val name : String)

@Serializable
data class Owner (val name : Name)

@Serializable
data class Email( val value : String){
    init {
        if(!value.contains("@"))
            throw IllegalArgumentException("Invalid email")
    }
}

@Serializable
data class Id (val id : Int){
    init {
        if(id <= 0)
            throw IllegalArgumentException("Id must be positive and non-zero")
    }
}