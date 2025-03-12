package pt.isel.ls.domain

import kotlinx.serialization.Serializable

@Serializable
data class Uid (val id : Int) {init {
    require(id > 0) { "id must be positive" }
}}

@Serializable
data class UserName (val name : String)

@Serializable
data class Email (val name : String) {init {
    require("@" in name) { "Email must have @ in it"  }
}}

@Serializable
data class Id (val id : Int){
    init {
        if(id <= 0)
            throw IllegalArgumentException("Id must be positive and non-zero")
    }
}