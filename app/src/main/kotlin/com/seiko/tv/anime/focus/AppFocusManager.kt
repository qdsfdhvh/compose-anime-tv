package com.seiko.tv.anime.focus

import android.view.KeyEvent
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager

object AppFocusManager {

  var focusManager: FocusManager? = null

  fun onKeyDown(keyCode: Int): Boolean? {
    return when (keyCode) {
      KeyEvent.KEYCODE_DPAD_DOWN -> focusManager?.moveFocus(FocusDirection.Down)
      KeyEvent.KEYCODE_DPAD_UP -> focusManager?.moveFocus(FocusDirection.Up)
      KeyEvent.KEYCODE_DPAD_LEFT -> focusManager?.moveFocus(FocusDirection.Left)
      KeyEvent.KEYCODE_DPAD_RIGHT -> focusManager?.moveFocus(FocusDirection.Right)
      else -> null
    }
  }
}