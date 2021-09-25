package com.seiko.tv.anime

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.google.accompanist.insets.ProvideWindowInsets
import com.seiko.compose.focuskit.TvKeyEvent
import com.seiko.compose.focuskit.handleTvKey
import com.seiko.tv.anime.ui.composer.assisted.ProvideAssistedMap
import com.seiko.tv.anime.ui.composer.navigation.AppNavigator
import com.seiko.tv.anime.ui.composer.navigation.Router
import com.seiko.tv.anime.ui.composer.screener.SmallScreener
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.util.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnimeTvActivity : ComponentActivity() {

  @Inject
  lateinit var assistedViewHolder: AnimeTvActivityAssistedViewHolder

  @Inject
  lateinit var smallScreeners: Set<@JvmSuppressWildcards SmallScreener>

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
      Box(
        modifier = Modifier
          .handleTvKey(TvKeyEvent.Back) {
            onBackPressed()
            true
          }
      ) {

        CompositionLocalProvider(
          LocalAppNavigator provides AppNavigator(navController),
        ) {
          ProvideWindowInsets {
            ProvideAssistedMap(assistedViewHolder.factory) {
              AnimeTvTheme {
                Router(navController)
              }
            }
          }
        }

        if (smallScreeners.isNotEmpty()) {
          smallScreeners.forEach {
            it.run { this@Box.Show() }
          }
        }
      }
    }
  }
}
