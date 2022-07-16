package com.seiko.tv.anime.ui.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberColorScheme(darkTheme: Boolean): ColorScheme {
  val context = LocalContext.current
  return remember(darkTheme, context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      if (darkTheme) dynamicDarkColorScheme(context)
      else dynamicLightColorScheme(context)
    } else {
      if (darkTheme) darkColorScheme()
      else lightColorScheme()
    }
  }
}
