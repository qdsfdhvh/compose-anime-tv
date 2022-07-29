package com.seiko.tv.anime.di.module

import org.koin.core.module.Module
import org.koin.dsl.module

val storageModule = module {
  setupStorageService()
}

internal expect fun Module.setupStorageService()

expect class StorageService {
  val appDir: String
  val cacheDir: String
}
