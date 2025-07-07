package pt.isel.ls.webApi.models.user

import kotlinx.serialization.Serializable
import pt.isel.ls.domain.User
import pt.isel.ls.utils.Id
import pt.isel.ls.utils.serializers.UUIDSerializer
import java.util.UUID

@Serializable
class UserResponse private constructor(
    @Serializable(with = UUIDSerializer::class)
    val token: UUID,
    val uid : Int
){
    companion object {
        operator fun invoke(user : User) : UserResponse {
            return UserResponse(user.token, user.uid.id)
        }
    }
}