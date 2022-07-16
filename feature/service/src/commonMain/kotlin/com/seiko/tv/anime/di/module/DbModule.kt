package com.seiko.tv.anime.di.module

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.seiko.tv.anime.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal val dbModule = module {
  setupDriverFactory()
  single {
    createDatabase(get())
  }
}

private fun createDatabase(driverFactory: DriverFactory): AppDatabase {
  val driver = driverFactory.createDriver(AppDatabase.Schema, "anime.db")
  return AppDatabase(driver)
}

internal expect fun Module.setupDriverFactory()

internal expect class DriverFactory {
  fun createDriver(schema: SqlSchema, dbName: String): SqlDriver
}
