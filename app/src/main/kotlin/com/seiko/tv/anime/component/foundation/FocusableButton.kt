package com.seiko.tv.anime.component.foundation

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.seiko.compose.focuskit.TvKeyEvent
import com.seiko.compose.focuskit.handleTvKey

@Composable
fun FocusableButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isFocused by interactionSource.collectIsFocusedAsState()

  val colors = ButtonDefaults.buttonColors(
    backgroundColor = if (isFocused) {
      MaterialTheme.colors.secondary
    } else {
      MaterialTheme.colors.surface
    }
  )

  Button(
    onClick = onClick,
    modifier = modifier
      .handleTvKey(TvKeyEvent.Enter) {
        onClick()
        true
      }
      .focusable(interactionSource = interactionSource),
    colors = colors,
    content = content
  )
}
