package com.seiko.tv.anime.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

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
private fun rememberColorScheme(darkTheme: Boolean): ColorScheme {
  val context = LocalContext.current
  return remember(darkTheme, context) {
    if (isDynamicColorSupported) {
      if (darkTheme) dynamicDarkColorScheme(context)
      else dynamicLightColorScheme(context)
    } else {
      if (darkTheme) darkColorScheme()
      else lightColorScheme()
    }
  }
}

private val isDynamicColorSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
