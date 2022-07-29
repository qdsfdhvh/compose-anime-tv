package com.seiko.tv.anime

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.ImageLoaderBuilder
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.disk.DiskCacheBuilder
import com.seiko.imageloader.cache.memory.MemoryCacheBuilder
import com.seiko.tv.anime.di.module.StorageService
import moe.tlaster.koin.get
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.ui.BackDispatcher
import moe.tlaster.precompose.ui.BackDispatcherOwner
import moe.tlaster.precompose.ui.LocalBackDispatcherOwner
import okio.Path.Companion.toPath
import org.koin.core.logger.Level

private val navigator by lazy {
  Navigator()
}

fun main(args: Array<String>) {
  startAppKoin {
    printLogger(Level.NONE)
  }
  application {
    PreComposeWindow(
      title = "AnimeTV",
      onCloseRequest = ::exitApplication,
    ) {
      CompositionLocalProvider(
        LocalBackDispatcherOwner provides DesktopBackDispatcherOwner,
        LocalImageLoader provides generateImageLoader(get()),
      ) {
        App(
          onBack = { navigator.goBack() },
          navigator = navigator,
          modifier = Modifier.fillMaxSize(),
        )
      }
    }
  }
}

private object DesktopBackDispatcherOwner : BackDispatcherOwner {
  override val backDispatcher: BackDispatcher by lazy(LazyThreadSafetyMode.NONE) {
    BackDispatcher()
  }
}

private fun generateImageLoader(storageService: StorageService): ImageLoader {
  return ImageLoaderBuilder()
    .memoryCache {
      MemoryCacheBuilder()
        // Set the max size to 25% of the app's available memory.
        .maxSizePercent(0.25)
        .build()
    }
    .diskCache {
      DiskCacheBuilder()
        .directory(storageService.cacheDir.toPath().resolve("image_cache"))
        .maxSizeBytes(512L * 1024 * 1024) // 512MB
        .build()
    }
    .build()
}
