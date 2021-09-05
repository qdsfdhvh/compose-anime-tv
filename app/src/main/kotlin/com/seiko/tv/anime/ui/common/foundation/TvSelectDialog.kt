package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.focusClick
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
    onDismissRequest = { onCancelClick() },
    title = { Text(text = title) },
    text = { Text(text) },
    confirmButton = {
      val interactionSource = remember { MutableInteractionSource() }
      TvSelectDialogButton(
        modifier = Modifier
          .focusClick { onCancelClick() }
          .focusable(interactionSource = interactionSource),
        text = cancelText,
        isFocused = interactionSource.collectIsFocusedAsState().value,
      )
    },
    dismissButton = {
      val interactionSource = remember { MutableInteractionSource() }
      TvSelectDialogButton(
        modifier = Modifier
          .focusClick { onCenterClick() }
          .focusRequester(focusRequester)
          .focusable(interactionSource = interactionSource),
        text = centerText,
        isFocused = interactionSource.collectIsFocusedAsState().value,
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
        MaterialTheme.shapes.small
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
