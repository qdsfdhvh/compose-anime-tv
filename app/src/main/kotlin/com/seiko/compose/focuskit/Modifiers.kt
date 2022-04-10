package com.seiko.compose.focuskit

import androidx.compose.foundation.clickable
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
fun Modifier.handleEnter(onAction: () -> Unit) =
  handleDirection(FocusDirection.In) { onAction(); true }

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.handleBack(onAction: () -> Unit) =
  handleDirection(FocusDirection.Out) { onAction(); true }

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.handleEnterReturn(onAction: () -> Boolean) =
  handleDirection(FocusDirection.In, onAction)

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.handleBackReturn(onAction: () -> Boolean) =
  handleDirection(FocusDirection.Out, onAction)

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.focusClick(onClick: () -> Unit) =
  clickable(onClick = onClick).handleEnter(onClick)
