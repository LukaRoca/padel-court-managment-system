package test

import DataMem
import pt.isel.ls.webServices.IServices
import pt.isel.ls.data.Data
import kotlin.test.BeforeTest

abstract class AbstractServicesTests {
    protected lateinit var storage: Data
    protected lateinit var services: IServices

    @BeforeTest
    fun setup() {
        storage = DataMem().apply { reset() }
        services = IServices(storage)
    }
}
