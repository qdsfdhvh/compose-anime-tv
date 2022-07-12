package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.ui.theme.backgroundColor
import com.seiko.tv.anime.ui.theme.uiValue

@Composable
fun RoundIcon(
  image: ImageVector,
  name: String,
  isFocused: Boolean,
  modifier: Modifier = Modifier,
  background: Color = MaterialTheme.colorScheme.surface,
  contentDescription: String? = null
) {
  val painter = rememberVectorPainter(image = image)
  val scale by animateFloatAsState(if (isFocused) 1.2f else 1f)
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Icon(
      painter = painter,
      contentDescription = contentDescription,
      modifier = modifier
        .scale(scale)
        .padding(16.dp)
        .shadow(if (isFocused) MaterialTheme.uiValue.elevation else 0.dp, CircleShape)
        .background(background, CircleShape)
        .padding(MaterialTheme.uiValue.paddingHorizontalSmall)
        .size(40.dp)
    )
    Text(
      modifier = Modifier.padding(top = 4.dp),
      text = name,
      style = MaterialTheme.typography.labelMedium
    )
  }
}

@Preview
@Composable
fun RoundIconPreview() {
  Row(
    modifier = Modifier
      .background(backgroundColor)
      .padding(20.dp)
  ) {
    RoundIcon(image = Icons.Filled.Settings, name = "设置", isFocused = true)
    Spacer(modifier = Modifier.width(20.dp))
    RoundIcon(image = Icons.Filled.Settings, name = "设置", isFocused = false)
  }
}
