package com.seiko.compose.focuskit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester

@Composable
fun rememberFocusRequesterManager(key: Any? = null): FocusRequesterManager {
  return remember(key) {
    FocusRequesterManager()
  }
}

class FocusRequesterManager internal constructor() {

  private val focusRequesters = hashMapOf<Any, FocusRequester>()

  infix operator fun get(key: Any): FocusRequester {
    return focusRequesters.getOrPut(key) { FocusRequester() }
  }

  fun getOrDefault(key: Any): FocusRequester {
    return focusRequesters.getOrDefault(key, FocusRequester.Default)
  }
}
