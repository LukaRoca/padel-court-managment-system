package data.users

interface UsersTest {
    fun createUsersSuccessfully()

    fun createUsersFailed()

    fun getUsersByIdSuccessfully()

    fun getUsersByIdFailed()

    fun getUsersByTokenSuccessfully()

    fun getUsersByTokenFailed()

    fun getAllUsersSuccessfully()

    fun getAllUsersFailed()
}
