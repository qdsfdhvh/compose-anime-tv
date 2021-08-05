package com.seiko.tv.anime

import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.google.accompanist.insets.ProvideWindowInsets
import com.seiko.tv.anime.navigation.AppNavigator
import com.seiko.tv.anime.navigation.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import dagger.hilt.android.AndroidEntryPoint

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

    setContent {
      CompositionLocalProvider(
        LocalAppNavigator provides AppNavigator(navController)
      ) {
        AnimeTvTheme {
          // https://stackoverflow.com/questions/65260293
          ProvideWindowInsets {
            Router(navController)
          }
        }
      }
    }
  }
}