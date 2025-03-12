package pt.isel.ls.domain

data class Number (val rid : Int)

data class Date (val year : Int, val month : Int, val day : Int)

data class Duration (val hours : Int)


data class Rental (val rid : Number, val date : Date, val duration : Duration, val user : User, val court : Court) {
    init {
        require(duration.hours > 0) { "hours must be > 0" }
        require(date.year > 0 && date.month > 0 && date.day > 0) {"The Date must be greater than 0"}
    }
}
