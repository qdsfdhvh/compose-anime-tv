package com.seiko.tv.anime

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
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
import com.seiko.compose.focuskit.TvControllerKey
import com.seiko.compose.focuskit.TvLogger
import com.seiko.compose.focuskit.handleTvKey
import com.seiko.tv.anime.navigation.AppNavigator
import com.seiko.tv.anime.navigation.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AnimeTvActivity : ComponentActivity() {

  @Inject
  lateinit var assistedViewHolder: AnimeTvActivityAssistedViewHolder

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

    TvLogger.setLogger(object : TvLogger {
      override var level: Int = Log.DEBUG
      override fun log(level: Int, msg: String?, throwable: Throwable?) {
        if (msg != null) {
          Timber.tag("Focuskit").log(level, msg)
        }
        if (throwable != null) {
          Timber.tag("Focuskit").log(level, msg, throwable)
        }
      }
    })

    val navigator = AppNavigator(navController)

    setContent {
      CompositionLocalProvider(
        LocalAppNavigator provides navigator,
        LocalAssistedFactoryMap provides assistedViewHolder.factory
      ) {
        AnimeTvTheme {
          ProvideWindowInsets {
            Box(
              modifier = Modifier
                .handleTvKey(TvControllerKey.Back) {
                  if (!navigator.pop()) {
                    ActivityCompat.finishAffinity(this)
                  }
                  true
                }
            ) {
              Router(navController)
            }
          }
        }
      }
    }
  }
}
