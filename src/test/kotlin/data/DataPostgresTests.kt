package data

import pt.isel.ls.CONN_NAME
import pt.isel.ls.data.dataPostgres.DataPostgres

abstract class DataPostgresTests : DataTests(DataPostgres(System.getenv(CONN_NAME)))