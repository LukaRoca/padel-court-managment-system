package data

import pt.isel.ls.CONN_NAME
import pt.isel.ls.data.dataPostgres.DataPostgres

abstract class DataPostgresTests : DataTests(DataPostgres("jdbc:postgresql://localhost/postgres?user=postgres&password=tubarao"))