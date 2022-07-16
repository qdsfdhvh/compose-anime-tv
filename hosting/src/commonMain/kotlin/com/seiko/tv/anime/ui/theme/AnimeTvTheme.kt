package com.seiko.tv.anime.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val typography = Typography()
private val shapes = Shapes()

@Composable
fun AnimeTvTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = rememberColorScheme(darkTheme),
    typography = typography,
    shapes = shapes,
    content = {
      CompositionLocalProvider(
        LocalUiValue provides UiValue(),
        content = content
      )
    }
  )
}

@Composable
expect fun rememberColorScheme(darkTheme: Boolean): ColorScheme
