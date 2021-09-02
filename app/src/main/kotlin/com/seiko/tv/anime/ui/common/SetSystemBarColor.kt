package com.seiko.tv.anime.ui.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * 配置状态栏
 */
@Composable
fun SetSystemBarColor(
  color: Color = Color.Transparent,
  useDarkIcons: Boolean = MaterialTheme.colors.isLight
) {
  val systemUiController = rememberSystemUiController()
  SideEffect {
    systemUiController.setSystemBarsColor(
      color = color,
      darkIcons = useDarkIcons
    )
  }
}
