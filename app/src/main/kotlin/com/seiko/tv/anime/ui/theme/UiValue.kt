package com.seiko.tv.anime.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
class UiValue(
  val paddingHorizontalSmall: Dp = 10.dp,
  val paddingHorizontal: Dp = 15.dp,
  val paddingVerticalSmall: Dp = 10.dp,
  val paddingVertical: Dp = 10.dp,
  val elevation: Dp = 2.dp
)

val MaterialTheme.uiValue: UiValue
  @Composable
  @ReadOnlyComposable
  get() = LocalUiValue.current

internal val LocalUiValue = staticCompositionLocalOf { UiValue() }