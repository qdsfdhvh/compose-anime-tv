package com.seiko.compose.player.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SmallSeekBar(
  progress: Long,
  max: Long,
  modifier: Modifier = Modifier,
  secondaryProgress: Long? = null,
  color: Color = MaterialTheme.colors.primary,
  secondaryColor: Color = color.copy(0.6f),
  backgroundColor: Color = Color.White.copy(alpha = 0.4f),
) {
  Canvas(modifier = modifier.height(4.dp)) {
    drawRoundRect(backgroundColor, 1f)
    drawRoundRect(secondaryColor, secondaryProgress?.let { it.toFloat() / max })
    drawRoundRect(color, progress.toFloat() / max)
  }
}

private fun DrawScope.drawRoundRect(color: Color, percent: Float?) {
  if (percent == null) return
  drawRoundRect(
    color = color,
    size = Size(percent * size.width, size.height),
    cornerRadius = CornerRadius(size.height / 2)
  )
}

@Preview(widthDp = 100)
@Composable
fun SmallSeekBarPreview() {
  SmallSeekBar(
    modifier = Modifier.fillMaxWidth(),
    progress = 10,
    secondaryProgress = 30,
    max = 100,
  )
}
