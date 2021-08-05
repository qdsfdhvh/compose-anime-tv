package com.seiko.tv.anime

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.google.accompanist.insets.ProvideWindowInsets
import com.seiko.compose.focuskit.LocalRootTvFocusItem
import com.seiko.compose.focuskit.Logger
import com.seiko.compose.focuskit.RootTvFocusItem
import com.seiko.compose.focuskit.tvFocusable
import com.seiko.tv.anime.navigation.AppNavigator
import com.seiko.tv.anime.navigation.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AnimeTvActivity : ComponentActivity() {

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

    Logger.setLogger(object : Logger {
      override fun log(level: Int, msg: String) {
        Timber.tag("Focuskit").log(level, msg)
      }
    })

    setContent {
      CompositionLocalProvider(
        LocalAppNavigator provides AppNavigator(navController),
        LocalRootTvFocusItem provides RootTvFocusItem()
      ) {
        AnimeTvTheme {
          // https://stackoverflow.com/questions/65260293
          ProvideWindowInsets {
            Box(
              modifier = Modifier
                .fillMaxSize()
                .tvFocusable()
            ) {
              Router(navController)
            }
          }
        }
      }
    }
  }
}