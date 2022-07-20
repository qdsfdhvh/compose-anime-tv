package com.seiko.tv.anime.ui.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
fun FocusableButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable RowScope.(Boolean) -> Unit
) {
  var isFocused by remember { mutableStateOf(false) }
  val scale by animateFloatAsState(if (isFocused) 1.1f else 1f)

  Button(
    onClick = onClick,
    modifier = modifier
      .onFocusChanged { isFocused = it.isFocused }
      .focusTarget()
      .scale(scale),
    elevation = null,
    content = {
      content(isFocused)
    }
  )
}

@Composable
fun FocusableImageButton(
  image: ImageVector,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  contentDescription: String? = null
) {
  FocusableButton(
    modifier = modifier,
    onClick = onClick
  ) {
    Icon(
      painter = rememberVectorPainter(image),
      contentDescription = contentDescription
    )
  }
}

@Composable
fun FocusableTextButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  FocusableButton(
    modifier = modifier,
    onClick = onClick
  ) {
    Text(text)
  }
}
