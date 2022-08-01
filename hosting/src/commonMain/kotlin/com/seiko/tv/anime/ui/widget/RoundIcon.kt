package com.seiko.tv.anime.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  contentDescription: String? = null
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(
      containerColor = Color.Transparent,
      contentColor = MaterialTheme.colorScheme.onBackground,
    ),
    contentPadding = PaddingValues(8.dp),
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      val scale by animateFloatAsState(if (isFocused) 1.2f else 1f)
      Surface(
        shape = CircleShape,
        tonalElevation = 1.dp,
        shadowElevation = if (isFocused) 1.dp else 0.dp,
        modifier = Modifier
          .scale(scale)
          .padding(8.dp)
          .size(60.dp),
      ) {
        Icon(
          painter = rememberVectorPainter(image),
          contentDescription = contentDescription,
          modifier = Modifier.padding(16.dp),
        )
      }
      Text(text = name)
    }
  }
}
