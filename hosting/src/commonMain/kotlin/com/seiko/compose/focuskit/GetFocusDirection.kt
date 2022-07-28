package com.seiko.compose.focuskit

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type

@OptIn(ExperimentalComposeUiApi::class)
fun getFocusDirection(keyEvent: KeyEvent): FocusDirection? {
  if (keyEvent.type != KeyEventType.KeyDown) return null
  return when (keyEvent.key) {
    Key.Tab -> if (keyEvent.isShiftPressed) FocusDirection.Previous else FocusDirection.Next
    Key.DirectionRight -> FocusDirection.Right
    Key.DirectionLeft -> FocusDirection.Left
    Key.DirectionUp -> FocusDirection.Up
    Key.DirectionDown -> FocusDirection.Down
    Key.DirectionCenter, Key.Enter -> FocusDirection.In
    Key.Back, Key.Escape -> FocusDirection.Out
    else -> null
  }
}
