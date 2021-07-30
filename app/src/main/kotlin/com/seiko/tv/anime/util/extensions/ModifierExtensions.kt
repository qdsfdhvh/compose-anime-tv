package com.seiko.tv.anime.util.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.*

/**
 * 焦点
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.focusTarget(
  focusRequester: FocusRequester,
  onFocusChanged: (FocusState) -> Unit
) = composed {
  onFocusChanged(onFocusChanged)
    .focusRequester(focusRequester)
    .focusTarget()
    // noRipple https://stackoverflow.com/a/66839858/14299073
    .clickable(
      indication = null,
      interactionSource = remember { MutableInteractionSource() },
      onClick = { focusRequester.requestFocus() }
    )
}