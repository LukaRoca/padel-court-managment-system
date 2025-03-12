package pt.isel.ls.domain

data class Number (val rid : Int)

data class Date (val year : Int, val month : Int, val day : Int) {
    init {
        require(year > 0 && month > 0 && day > 0) {"The Date must be greater than 0"}
    }
}

data class Duration (val hours : Int) {
    init {
        require(hours > 0) { "hours must be > 0" }
    }
}

data class User (val id : Int)

data class Court(val name : String)

data class Rental (val rid : Number, val date : Date, val duration : Duration, val user : User, val court : Court) {

}
