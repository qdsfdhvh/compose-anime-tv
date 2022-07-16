package com.seiko.tv.anime.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun PlatformDialog(
  onDismissRequest: () -> Unit,
  confirmButton: @Composable () -> Unit,
  modifier: Modifier,
  dismissButton: @Composable (() -> Unit)?,
  title: @Composable (() -> Unit)?,
  text: @Composable (() -> Unit)?
) {
  // TODO PlatformDialog support ios
}