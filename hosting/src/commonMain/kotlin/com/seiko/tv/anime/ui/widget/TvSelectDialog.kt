package com.seiko.tv.anime.ui.widget

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import com.seiko.compose.focuskit.onFocusDirection
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
  val cancelFocusRequester = remember { FocusRequester() }
  val centerFocusRequester = remember { FocusRequester() }
  PlatformDialog(
    onDismissRequest = { onCancelClick() },
    title = { Text(title) },
    text = { Text(text) },
    confirmButton = {
      var isFocused by remember { mutableStateOf(false) }
      TvSelectDialogButton(
        modifier = Modifier
          .onFocusChanged {
            isFocused = it.isFocused
          }
          .focusProperties {
            left = centerFocusRequester
            right = centerFocusRequester
          }
          .focusRequester(cancelFocusRequester),
        text = cancelText,
        isFocused = isFocused,
        onClick = onCancelClick,
      )
    },
    dismissButton = {
      var isFocused by remember { mutableStateOf(false) }
      TvSelectDialogButton(
        modifier = Modifier
          .onFocusChanged {
            isFocused = it.isFocused
          }
          .focusProperties {
            left = cancelFocusRequester
            right = cancelFocusRequester
          }
          .focusRequester(centerFocusRequester),
        text = centerText,
        isFocused = isFocused,
        onClick = onCenterClick,
      )
      LaunchedEffect(Unit) {
        centerFocusRequester.requestFocus()
      }
    },
    modifier = Modifier.onFocusDirection {
      when (it) {
        FocusDirection.Up,
        FocusDirection.Down -> true
        else -> false
      }
    }
  )
}

@Composable
fun TvSelectDialogButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  text: String = "",
  isFocused: Boolean = false
) {
  TextButton(
    shape = MaterialTheme.shapes.small,
    colors = ButtonDefaults.textButtonColors(
      containerColor = if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent,
      contentColor = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
    ),
    modifier = modifier.focusTarget(),
    onClick = onClick,
  ) {
    Text(text)
  }
}
