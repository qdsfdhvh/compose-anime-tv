package com.seiko.tv.anime.di.module

import android.content.Context
import org.koin.core.module.Module

internal actual fun Module.setupStorageService() {
  single { StorageService(get()) }
}

actual class StorageService(private val context: Context) {
  actual val appDir: String
    get() = "${context.filesDir.absolutePath}/app"
  actual val cacheDir: String
    get() = "${context.cacheDir}/caches"
}
