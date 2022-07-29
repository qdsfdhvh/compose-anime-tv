package com.seiko.tv.anime.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.util.PlatformDialog

@Composable
fun TvSelectDialog(
  title: String = "提示",
  text: String = "",
  centerText: String = "确认",
  cancelText: String = "取消",
  onCenterClick: () -> Unit = {},
  onCancelClick: () -> Unit = {}
) {
  PlatformDialog(
    onDismissRequest = { onCancelClick() },
    title = { Text(title) },
    text = { Text(text) },
    confirmButton = {
      var isFocused by remember { mutableStateOf(false) }
      TvSelectDialogButton(
        modifier = Modifier
          .onFocusChanged { isFocused = it.isFocused }
          .clickable { onCancelClick() }
          .focusTarget(),
        text = cancelText,
        isFocused = isFocused,
      )
    },
    dismissButton = {
      val focusRequester = remember { FocusRequester() }
      var isFocused by remember { mutableStateOf(false) }
      TvSelectDialogButton(
        modifier = Modifier
          .onFocusChanged { isFocused = it.isFocused }
          .clickable { onCenterClick() }
          .focusRequester(focusRequester)
          .focusTarget(),
        text = centerText,
        isFocused = isFocused,
      )
      LaunchedEffect(Unit) {
        focusRequester.requestFocus()
      }
    }
  )
}

@Composable
fun TvSelectDialogButton(
  modifier: Modifier = Modifier,
  text: String = "",
  isFocused: Boolean = false
) {
  Text(
    text = text,
    color = if (isFocused) MaterialTheme.colorScheme.onPrimary else Color.Unspecified,
    style = MaterialTheme.typography.labelMedium,
    modifier = modifier
      .background(
        if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent,
        MaterialTheme.shapes.small
      )
      .padding(horizontal = 12.dp, vertical = 4.dp)
  )
}
