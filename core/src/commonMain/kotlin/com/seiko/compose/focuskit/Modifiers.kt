package com.seiko.compose.focuskit

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent

fun Modifier.onPreviewFocusDirection(
  onPreviewFocusDirection: (FocusDirection) -> Boolean
) = this.onPreviewKeyEvent {
  getFocusDirection(it)?.run(onPreviewFocusDirection) ?: false
}

fun Modifier.onFocusDirection(
  onFocusDirection: (FocusDirection) -> Boolean
) = this.onKeyEvent {
  getFocusDirection(it)?.run(onFocusDirection) ?: false
}

fun Modifier.handleDirection(
  focusDirection: FocusDirection,
  onAction: () -> Boolean
) = this.onFocusDirection {
  if (it == focusDirection) onAction()
  else false
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.handleEnterReturn(onAction: () -> Boolean) =
  handleDirection(FocusDirection.In, onAction)

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.handleBackReturn(onAction: () -> Boolean) =
  handleDirection(FocusDirection.Out, onAction)
