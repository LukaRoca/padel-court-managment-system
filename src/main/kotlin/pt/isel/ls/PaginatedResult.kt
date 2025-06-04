package pt.isel.ls

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResult<T>(
    val list : List<T>,
    val next : Boolean,
    val previous : Boolean
)
fun <T> List<T>.paginate(limit : Int, skip: Int) : List<T> {
    if (this.isEmpty() || skip >= size) {
        return emptyList()
    }
    val lastIndex: Int = if (limit + skip > size) size else limit + skip
    return subList(skip, lastIndex)
}

fun <T> List<T>.paginateWithInfo(limit: Int, skip: Int): PaginatedResult<T> {
    val paginatedList = this.paginate(limit, skip)
    val hasPrev = skip > 0
    val hasNext = skip + limit < this.size
    return PaginatedResult(paginatedList, hasNext, hasPrev )
}

