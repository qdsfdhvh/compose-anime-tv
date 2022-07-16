package com.seiko.tv.anime.util

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun PlatformDialog(
  onDismissRequest: () -> Unit,
  confirmButton: @Composable () -> Unit,
  modifier: Modifier,
  dismissButton: @Composable (() -> Unit)?,
  title: @Composable (() -> Unit)?,
  text: @Composable (() -> Unit)?,
) {
  AlertDialog(
    onDismissRequest = onDismissRequest,
    confirmButton = confirmButton,
    modifier = modifier,
    dismissButton = dismissButton,
    title = title,
    text = text,
  )
}