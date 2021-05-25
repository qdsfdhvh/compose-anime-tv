package com.seiko.tv.anime

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import com.seiko.tv.anime.navigation.AppNavigator
import com.seiko.tv.anime.navigation.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeTvActivity : ComponentActivity() {

  private val navController by lazy {
    NavHostController(this).apply {
      navigatorProvider.addNavigator(ComposeNavigator())
    }
  }

  private var focusManager: FocusManager? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    // set Color.TRANSPARENT is not fully transparent
    window.navigationBarColor = Color.parseColor("#01FFFFFF")
    window.statusBarColor = Color.TRANSPARENT

    setContent {
      focusManager = LocalFocusManager.current

      CompositionLocalProvider(
        LocalAppNavigator provides AppNavigator(navController)
      ) {
        AnimeTvTheme {
          Router(navController)
        }
      }
    }
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
    val result = when (keyCode) {
      KeyEvent.KEYCODE_DPAD_DOWN -> focusManager?.moveFocus(FocusDirection.Down)
      KeyEvent.KEYCODE_DPAD_UP -> focusManager?.moveFocus(FocusDirection.Up)
      KeyEvent.KEYCODE_DPAD_LEFT -> focusManager?.moveFocus(FocusDirection.Left)
      KeyEvent.KEYCODE_DPAD_RIGHT -> focusManager?.moveFocus(FocusDirection.Right)
      else -> false
    }
    if (result == true) {
      return true
    }
    return super.onKeyDown(keyCode, event)
  }
}