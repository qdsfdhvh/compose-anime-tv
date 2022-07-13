package com.seiko.tv.anime

import android.os.Bundle
import android.view.WindowManager
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.core.view.WindowCompat
import com.seiko.compose.focuskit.handleBack
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.ImageLoaderBuilder
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.disk.DiskCacheBuilder
import com.seiko.imageloader.cache.memory.MemoryCacheBuilder
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.util.DoubleBackPressed
import com.seiko.tv.anime.util.DoubleBackPressedDelegate
import com.seiko.tv.anime.util.NoRippleIndication
import com.seiko.tv.anime.util.ToastScreenComponent
import com.seiko.tv.anime.util.autoSizeDensity
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.rememberNavigator
import okio.Path.Companion.toOkioPath

class AnimeTvActivity : PreComposeActivity(), DoubleBackPressed by DoubleBackPressedDelegate() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    setContent {
      AnimeTvTheme(false) {
        CompositionLocalProvider(
          LocalIndication provides NoRippleIndication,
          LocalDensity provides generateDensity(),
          LocalImageLoader provides generateImageLoader(),
        ) {
          BackHandler {
            onDoubleBackPressed()
          }

          Box(Modifier.handleBack { onBackPressed() }) {
            Router(
              navigator = rememberNavigator()
            )

            ToastScreenComponent()
          }
        }
      }
    }
  }

  private fun generateDensity(): Density {
    return autoSizeDensity(this, 480)
  }

  private fun generateImageLoader(): ImageLoader {
    return ImageLoaderBuilder(this)
      .memoryCache {
        MemoryCacheBuilder()
          // Set the max size to 25% of the app's available memory.
          .maxSizePercent(0.25)
          .build()
      }
      .diskCache {
        DiskCacheBuilder()
          .directory(cacheDir.resolve("image_cache").toOkioPath())
          .maxSizeBytes(512L * 1024 * 1024) // 512MB
          .build()
      }
      .build()
  }
}
