package com.seiko.tv.anime.di.module

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.core.module.Module

internal actual fun Module.setupDriverFactory() {
  single { DriverFactory() }
}

internal actual class DriverFactory {
  actual fun createDriver(schema: SqlSchema, dbName: String): SqlDriver {
    return NativeSqliteDriver(schema, dbName)
  }
}
