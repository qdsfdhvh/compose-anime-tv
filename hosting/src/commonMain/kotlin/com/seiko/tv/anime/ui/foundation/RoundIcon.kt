package com.seiko.tv.anime.ui.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

@Composable
fun RoundIcon(
  image: ImageVector,
  name: String,
  isFocused: Boolean,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.colorScheme.surface,
  contentColor: Color = contentColorFor(color),
  contentDescription: String? = null
) {
  val painter = rememberVectorPainter(image = image)
  val scale by animateFloatAsState(if (isFocused) 1.2f else 1f)
  Surface(
    modifier = modifier,
    color = color,
    contentColor = contentColor,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Surface(
        shape = CircleShape,
        tonalElevation = 1.dp,
        shadowElevation = if (isFocused) 1.dp else 0.dp,
        modifier = Modifier
          .scale(scale)
          .padding(16.dp)
          .size(60.dp),
      ) {
        Icon(
          painter = painter,
          contentDescription = contentDescription,
          modifier = Modifier.padding(16.dp),
        )
      }
      Text(text = name)
    }
  }
}
