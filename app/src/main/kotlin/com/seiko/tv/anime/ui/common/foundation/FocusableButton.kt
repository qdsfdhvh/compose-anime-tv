package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.handleEnter
import com.seiko.tv.anime.ui.theme.uiValue

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
      .handleEnter(onClick)
      .onFocusChanged { isFocused = it.isFocused }
      .focusTarget()
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

@Composable
fun FocusableImageButton(
  image: ImageVector,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  contentDescription: String? = null,
) {
  FocusableButton(
    modifier = modifier,
    onClick = onClick,
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
  modifier: Modifier = Modifier,
) {
  FocusableButton(
    modifier = modifier,
    onClick = onClick,
  ) {
    Text(text)
  }
}
