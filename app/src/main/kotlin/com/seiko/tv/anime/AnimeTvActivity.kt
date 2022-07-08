package com.seiko.tv.anime

import android.os.Bundle
import android.view.WindowManager
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.core.view.WindowCompat
import com.seiko.compose.focuskit.handleBack
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.util.DoubleBackPressed
import com.seiko.tv.anime.util.DoubleBackPressedDelegate
import com.seiko.tv.anime.util.NoRippleIndication
import com.seiko.tv.anime.util.ToastScreenComponent
import com.seiko.tv.anime.util.autoSizeDensity
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent
import moe.tlaster.precompose.navigation.rememberNavController

class AnimeTvActivity : PreComposeActivity(), DoubleBackPressed by DoubleBackPressedDelegate() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    backDispatcher.addCallback {
      onDoubleBackPressed()
    }

    setContent {
      AnimeTvTheme {
        CompositionLocalProvider(
          LocalIndication provides NoRippleIndication,
          LocalDensity provides autoSizeDensity(this@AnimeTvActivity, 480)
        ) {
          Box(Modifier.handleBack { onBackPressed() }) {
            Router(
              navController = rememberNavController()
            )

            ToastScreenComponent()
          }
        }
      }
    }
  }
}
