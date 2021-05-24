package com.seiko.tv.anime

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import com.seiko.tv.anime.navigation.AppNavigator
import com.seiko.tv.anime.navigation.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val navController by lazy {
    NavHostController(this).apply {
      navigatorProvider.addNavigator(ComposeNavigator())
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    // set Color.TRANSPARENT is not fully transparent
    window.navigationBarColor = Color.parseColor("#01FFFFFF")
    window.statusBarColor = Color.TRANSPARENT

    setContent {
      CompositionLocalProvider(
        LocalAppNavigator provides AppNavigator(navController)
      ) {
        AnimeTvTheme {
          Router(navController)
        }
      }
    }
  }
}