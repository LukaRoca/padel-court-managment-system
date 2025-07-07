package pt.isel.ls.webApi.models.user

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.PaginatedResponse

@Serializable
class UserListResponse private constructor(
    val users : List<UserListElement>,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val total: Int
) {
    companion object {
        operator fun invoke(users : PaginatedResponse<UserListElement>) : UserListResponse {
            val (user, hasNext, hasPrevious) = users
            return UserListResponse(user, hasNext,hasPrevious, user.size)
        }
    }
}