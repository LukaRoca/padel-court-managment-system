package data.clubs

interface ClubTest {
    fun createClubSuccessfully()

    fun createClubFailed()

    fun getClubByIdSuccessfully()

    fun getClubByIdFailed()

    fun getClubByNameSuccessfully()

    fun getClubByNameFailed()

    fun getClubsSuccessfully()

    fun getClubsFailed()

    fun deleteClubSuccessfully()

    fun deleteClubFailed()
}
