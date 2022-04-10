package com.seiko.tv.anime

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.core.view.WindowCompat
import coil.ImageLoader
import coil.compose.LocalImageLoader
import com.google.accompanist.insets.ProvideWindowInsets
import com.seiko.compose.focuskit.handleBack
import com.seiko.tv.anime.ui.composer.navigation.AppNavigator
import com.seiko.tv.anime.ui.composer.navigation.AppNavigatorImpl
import com.seiko.tv.anime.ui.composer.navigation.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.util.DoubleBackPressed
import com.seiko.tv.anime.util.DoubleBackPressedDelegate
import com.seiko.tv.anime.util.NoRippleIndication
import com.seiko.tv.anime.util.ToastScreenComponent
import com.seiko.tv.anime.util.autoSizeDensity
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.activityScope
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

class AnimeTvActivity : ComponentActivity(), KoinScopeComponent, DoubleBackPressed by DoubleBackPressedDelegate() {

  override val scope: Scope by activityScope()

  private val imageLoader: ImageLoader by inject()

  private val appNavigator: AppNavigator by lazy(LazyThreadSafetyMode.NONE) {
    AppNavigatorImpl(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    onBackPressedDispatcher.addCallback(this) {
      onDoubleBackPressed()
    }

    setContent {
      AnimeTvTheme {
        ProvideWindowInsets {
          CompositionLocalProvider(
            LocalAppNavigator provides appNavigator,
            LocalImageLoader provides imageLoader,
            LocalIndication provides NoRippleIndication,
            LocalDensity provides autoSizeDensity(this@AnimeTvActivity, 480)
          ) {
            Box(Modifier.handleBack { onBackPressed() }) {
              Router(appNavigator)

              ToastScreenComponent()
            }
          }
        }
      }
    }
  }
}
