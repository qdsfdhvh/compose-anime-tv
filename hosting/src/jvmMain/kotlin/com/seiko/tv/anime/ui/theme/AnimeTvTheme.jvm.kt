package com.seiko.tv.anime.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun rememberColorScheme(darkTheme: Boolean): ColorScheme {
  return if (darkTheme) darkColorScheme()
  else lightColorScheme()
}
