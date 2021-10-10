package com.seiko.tv.anime

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import coil.ImageLoader
import coil.compose.LocalImageLoader
import com.google.accompanist.insets.ProvideWindowInsets
import com.seiko.compose.focuskit.handleBack
import com.seiko.tv.anime.di.scope.AssistedFactoryQualifier
import com.seiko.tv.anime.di.scope.CollectScreenComponentQualifier
import com.seiko.tv.anime.ui.composer.assisted.AssistedFactoryMap
import com.seiko.tv.anime.ui.composer.assisted.ProvideAssistedMap
import com.seiko.tv.anime.ui.composer.collector.CollectComposeOwner
import com.seiko.tv.anime.ui.composer.collector.Show
import com.seiko.tv.anime.ui.composer.navigation.AppNavigator
import com.seiko.tv.anime.ui.composer.navigation.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.util.NoRippleIndication
import com.seiko.tv.anime.util.ToastUtils
import com.seiko.tv.anime.util.autoSizeDensity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnimeTvActivity : ComponentActivity() {

  @Inject
  @AssistedFactoryQualifier
  lateinit var assistedFactoryMap: AssistedFactoryMap

  @Inject
  @CollectScreenComponentQualifier
  lateinit var collectScreenComponents: Set<@JvmSuppressWildcards CollectComposeOwner<BoxScope>>

  @Inject
  lateinit var imageLoader: ImageLoader

  private var lastClickTime = 0L

  private val navController by lazy {
    NavHostController(this).apply {
      navigatorProvider.addNavigator(ComposeNavigator())
      navigatorProvider.addNavigator(DialogNavigator())
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    onBackPressedDispatcher.addCallback(this) {
      val current = System.currentTimeMillis()
      if (current - lastClickTime >= 2000L) {
        lastClickTime = current
        ToastUtils.showToast(applicationContext, "再按一次退出")
      } else {
        ActivityCompat.finishAffinity(this@AnimeTvActivity)
      }
    }

    setContent {
      AnimeTvTheme {
        ProvideWindowInsets {
          ProvideAssistedMap(assistedFactoryMap) {
            CompositionLocalProvider(
              LocalAppNavigator provides AppNavigator(navController),
              LocalImageLoader provides imageLoader,
              LocalIndication provides NoRippleIndication,
              LocalDensity provides autoSizeDensity(this@AnimeTvActivity, 480)
            ) {
              Box(Modifier.handleBack { onBackPressed() }) {
                Router(navController)

                Show(collectScreenComponents)
              }
            }
          }
        }
      }
    }
  }
}
