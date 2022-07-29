package com.seiko.tv.anime.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seiko.tv.anime.widget.AlertDialogImpl

@Composable
fun PlatformDialog(
  onDismissRequest: () -> Unit,
  confirmButton: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  dismissButton: @Composable (() -> Unit)? = null,
  title: @Composable (() -> Unit)? = null,
  icon: @Composable (() -> Unit)? = null,
  text: @Composable (() -> Unit)? = null,
) {
  AlertDialogImpl(
    onDismissRequest = onDismissRequest,
    confirmButton = confirmButton,
    modifier = modifier,
    dismissButton = dismissButton,
    title = title,
    icon = icon,
    text = text,
  )
}
