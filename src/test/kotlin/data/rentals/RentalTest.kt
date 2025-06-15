package data.rentals

interface RentalTest {
    fun createRentalSuccessfully()

    fun createRentalFailed()

    fun getRentalByIdSuccessfully()

    fun getRentalByIdFailed()

    fun getRentalsOfUserSuccessfully()

    fun getRentalsOfUserFailed()

    fun getRentalsSuccessfully()

    fun getRentalsFailed()

    fun getRentalsOfCourtSuccessfully()

    fun getRentalsOfCourtFailed()

    fun getAvailableHoursSuccessfully()

    fun getAvailableHoursFailed()

    fun deleteRentalSuccessfully()

    fun deleteRentalFailed()

    fun updateRentalSuccessfully()

    fun updateRentalFailed()

    fun getRentalsWithDateSuccessfully()

    fun getRentalsWithDateFailed()
}
