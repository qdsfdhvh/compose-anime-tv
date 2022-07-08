package com.seiko.compose.player.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TimeTextBar(
  position: String,
  duration: String,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    TimeText(position)
    TimeText(duration)
  }
}

@Composable
fun TimeText(text: String) {
  Text(
    text = text,
    color = Color.White,
    style = MaterialTheme.typography.labelSmall
  )
}

@Preview
@Composable
fun TimeTextBarPreview() {
  TimeTextBar("00:00", "12:00")
}
