package com.seiko.tv.anime.di.module

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.core.module.Module

internal actual fun Module.setupDriverFactory() {
  single { DriverFactory(get()) }
}

internal actual class DriverFactory(private val context: Context) {
  actual fun createDriver(schema: SqlSchema, dbName: String): SqlDriver {
    return AndroidSqliteDriver(schema, context, dbName)
  }
}
