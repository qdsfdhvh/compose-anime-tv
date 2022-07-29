package com.seiko.tv.anime.di.module

import com.seiko.tv.anime.OperatingSystem
import com.seiko.tv.anime.currentOperatingSystem
import org.koin.core.module.Module
import java.io.File

internal actual fun Module.setupStorageService() {
  single { StorageService() }
}

actual class StorageService {
  actual val appDir: String
    get() = getAppDir().absolutePath.also { it.mkdirs() }
  actual val cacheDir: String
    get() = getCacheDir().absolutePath.also { it.mkdirs() }
}

private fun getAppDir() = when (currentOperatingSystem) {
  OperatingSystem.Windows -> File(System.getenv("AppData"), "$ApplicationName/app")
  OperatingSystem.Linux -> File(System.getProperty("user.home"), ".config/$ApplicationName")
  OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Application Support/$ApplicationName")
  else -> throw IllegalStateException("Unsupported operating system")
}

private fun getCacheDir() = when (currentOperatingSystem) {
  OperatingSystem.Windows -> File(System.getenv("AppData"), "$ApplicationName/cache")
  OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/$ApplicationName")
  OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Caches/$ApplicationName")
  else -> throw IllegalStateException("Unsupported operating system")
}

private fun String.mkdirs() {
  if (!File(this).exists()) {
    File(this).mkdirs()
  }
}

private const val ApplicationName = "com.seiko.tv.anime"
