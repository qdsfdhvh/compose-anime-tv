package com.seiko.tv.anime

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import com.seiko.compose.focuskit.handleDirection
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.util.NoRippleIndication
import com.seiko.tv.anime.util.ToastScreenComponent
import com.seiko.tv.anime.widget.ProvideDialogHost
import io.github.aakira.napier.Napier
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App(
  onBack: () -> Unit,
  modifier: Modifier = Modifier,
  navigator: Navigator = rememberNavigator(),
) {
  AnimeTvTheme(false) {
    CompositionLocalProvider(
      LocalIndication provides NoRippleIndication,
    ) {
      Box(
        modifier = modifier
          .onPreviewKeyEvent {
            if (it.type == KeyEventType.KeyDown) {
              Napier.d { "onPreviewKeyEvent $it" }
            }
            false
          }
          .handleDirection(FocusDirection.Out) {
            onBack.invoke()
            true
          }
      ) {
        ProvideDialogHost {
          Router(
            navigator = navigator,
          )
        }
        ToastScreenComponent()
      }
    }
  }
}
