package com.seiko.tv.anime.di.module

import org.koin.core.module.Module
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDirectory
import platform.Foundation.NSUserDomainMask

internal actual fun Module.setupStorageService() {
  single { StorageService() }
}

actual class StorageService {

  private val _appDir by lazy {
    NSFileManager.defaultManager.URLForDirectory(NSUserDirectory, NSUserDomainMask, null, true, null)!!
  }

  private val _cacheDir by lazy {
    NSFileManager.defaultManager.URLForDirectory(NSCachesDirectory, NSUserDomainMask, null, true, null)!!
  }

  actual val appDir: String
    get() = _appDir.path.orEmpty()
  actual val cacheDir: String
    get() = _cacheDir.path.orEmpty()
}
