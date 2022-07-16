package com.seiko.tv.anime.di.module

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.koin.core.module.Module

internal actual fun Module.setupDriverFactory() {
  single { DriverFactory(get()) }
}

internal actual class DriverFactory(private val storageService: StorageService) {
  actual fun createDriver(schema: SqlSchema, dbName: String): SqlDriver {
    val driver = JdbcSqliteDriver("$jcdbPrefix${storageService.appDir}/database/$dbName")
    schema.create(driver)
    return driver
  }
}

private const val jcdbPrefix = "jdbc:sqlite:"
