package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.TvKeyEvent
import com.seiko.compose.focuskit.handleTvKey
import com.seiko.tv.anime.ui.theme.uiValue

@Composable
fun FocusableButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.(Boolean) -> Unit
) {
  val isFocused by interactionSource.collectIsFocusedAsState()
  val scale by animateFloatAsState(if (isFocused) 1.1f else 1f)

  Button(
    onClick = onClick,
    modifier = modifier
      .handleTvKey(TvKeyEvent.Enter) {
        onClick()
        true
      }
      .focusable(interactionSource = interactionSource)
      .scale(scale),
    elevation = ButtonDefaults.elevation(
      defaultElevation = if (isFocused) MaterialTheme.uiValue.elevation else 0.dp
    ),
    colors = ButtonDefaults.buttonColors(
      backgroundColor = MaterialTheme.colors.surface
    ),
    content = {
      content(isFocused)
    }
  )
}
