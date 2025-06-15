package data.courts

interface CourtTest {
    fun createCourtSuccessfully()

    fun createCourtFailed()

    fun getCourtByIdSuccessfully()

    fun getCourtByIdFailed()

    fun getCourtsByClubIdSuccessfully()

    fun getCourtsByClubIdFailed()
}
