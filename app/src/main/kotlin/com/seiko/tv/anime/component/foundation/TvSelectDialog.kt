package com.seiko.tv.anime.component.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.TvControllerKey
import com.seiko.compose.focuskit.handleTvKey
import com.seiko.tv.anime.ui.theme.AnimeTvTheme

@Composable
fun TvSelectDialog(
  title: String = "提示",
  text: String = "",
  centerText: String = "确认",
  cancelText: String = "取消",
  onCenterClick: () -> Unit = {},
  onCancelClick: () -> Unit = {},
) {
  val focusRequester = remember { FocusRequester() }

  AlertDialog(
    onDismissRequest = { /* nothing to do */ },
    title = {
      Text(text = title)
    },
    text = {
      Text(text)
    },
    confirmButton = {
      var isFocused by remember { mutableStateOf(false) }
      TvSelectDialogButton(
        modifier = Modifier
          .handleTvKey(TvControllerKey.Enter) {
            onCancelClick()
            true
          }
          .clickable(onClick = onCancelClick)
          .onFocusChanged { isFocused = it.isFocused }
          .focusTarget(),
        text = cancelText,
        isFocused = isFocused,
      )
    },
    dismissButton = {
      var isFocused by remember { mutableStateOf(false) }
      TvSelectDialogButton(
        modifier = Modifier
          .handleTvKey(TvControllerKey.Enter) {
            onCenterClick()
            true
          }
          .clickable(onClick = onCenterClick)
          .focusRequester(focusRequester)
          .onFocusChanged { isFocused = it.isFocused }
          .focusTarget(),
        text = centerText,
        isFocused = isFocused,
      )
    }
  )

  LaunchedEffect(Unit) {
    focusRequester.requestFocus()
  }
}

@Composable
fun TvSelectDialogButton(
  modifier: Modifier = Modifier,
  text: String = "",
  isFocused: Boolean = false,
) {
  Text(
    text = text,
    color = if (isFocused) MaterialTheme.colors.onPrimary else Color.Unspecified,
    style = MaterialTheme.typography.button,
    modifier = modifier
      .background(
        if (isFocused) MaterialTheme.colors.primary else Color.Transparent,
        RoundedCornerShape(2.dp)
      )
      .padding(horizontal = 12.dp, vertical = 4.dp),
  )
}

@Preview
@Composable
fun TvSelectDialogButtonPreview() {
  AnimeTvTheme {
    Row {
      TvSelectDialogButton(text = "确认", isFocused = true)
      Spacer(modifier = Modifier.width(5.dp))
      TvSelectDialogButton(text = "取消", isFocused = false)
    }
  }
}