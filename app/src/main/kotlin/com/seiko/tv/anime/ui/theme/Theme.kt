package com.seiko.tv.anime.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.siddroid.holi.colors.CoolColor
import com.siddroid.holi.colors.MaterialColor

private val DarkColorPalette = darkColors(
  primary = CoolColor.DARK_GREY,
  primaryVariant = CoolColor.BLACK,
  secondary = CoolColor.BLUE
)

private val LightColorPalette = lightColors(
  primary = MaterialColor.PINK_200,
  primaryVariant = MaterialColor.PINK_700,
  secondary = MaterialColor.RED_300,
  background = backgroundColor,
  onBackground = textColor,
  surface = Color.White,
  onSurface = textColor,
)

@Composable
fun AnimeTvTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}