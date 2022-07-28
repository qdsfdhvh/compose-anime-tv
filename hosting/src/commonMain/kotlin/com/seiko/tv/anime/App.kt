package com.seiko.tv.anime

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import com.seiko.compose.focuskit.handleDirection
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.util.NoRippleIndication
import com.seiko.tv.anime.util.ToastScreenComponent
import moe.tlaster.precompose.navigation.rememberNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App(
  modifier: Modifier = Modifier,
) {
  AnimeTvTheme(false) {
    CompositionLocalProvider(
      LocalIndication provides NoRippleIndication,
    ) {
      val navigator = rememberNavigator()
      Box(
        modifier = modifier
          .handleDirection(FocusDirection.Out) {
            navigator.goBack()
            true
          }
      ) {
        Router(
          navigator = navigator,
        )
        ToastScreenComponent()
      }
    }
  }
}
