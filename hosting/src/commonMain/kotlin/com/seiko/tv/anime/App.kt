package com.seiko.tv.anime

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.util.NoRippleIndication
import com.seiko.tv.anime.util.ToastScreenComponent
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun App(
  modifier: Modifier = Modifier,
) {
  AnimeTvTheme(false) {
    CompositionLocalProvider(
      LocalIndication provides NoRippleIndication,
    ) {
      Box(modifier) {
        Router(
          navigator = rememberNavigator(),
        )
        ToastScreenComponent()
      }
    }
  }
}