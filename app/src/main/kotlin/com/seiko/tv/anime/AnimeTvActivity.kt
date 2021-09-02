package com.seiko.tv.anime

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.google.accompanist.insets.ProvideWindowInsets
import com.seiko.compose.focuskit.TvKeyEvent
import com.seiko.compose.focuskit.handleTvKey
import com.seiko.tv.anime.ui.composer.navigation.AppNavigator
import com.seiko.tv.anime.ui.composer.navigation.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.util.FpsHelper
import dagger.hilt.android.AndroidEntryPoint
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

    val navigator = AppNavigator(navController)
    val fpsFlow = FpsHelper.getFlow(lifecycleScope)

    setContent {
      CompositionLocalProvider(
        LocalAppNavigator provides navigator,
        LocalAssistedFactoryMap provides assistedViewHolder.factory
      ) {
        ProvideWindowInsets {
          AnimeTvTheme {
            Box(
              modifier = Modifier
                .handleTvKey(TvKeyEvent.Back) {
                  if (!navigator.pop()) {
                    ActivityCompat.finishAffinity(this)
                  }
                  true
                }
            ) {
              Router(navController)

              Text(
                text = "${fpsFlow.collectAsState().value}fps",
                color = Color.Red,
                modifier = Modifier
                  .align(Alignment.TopStart)
                  .padding(10.dp),
              )
            }
          }
        }
      }
    }
  }
}
