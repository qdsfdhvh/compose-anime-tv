package com.seiko.tv.anime.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.ui.theme.backgroundColor

@Preview
@Composable
fun RoundIconPreview() {
  Row(
    modifier = Modifier
      .background(backgroundColor)
      .padding(20.dp)
  ) {
    RoundIcon(image = Icons.Filled.Settings, name = "设置", isFocused = true, onClick = {})
    Spacer(modifier = Modifier.width(20.dp))
    RoundIcon(image = Icons.Filled.Settings, name = "设置", isFocused = false, onClick = {})
  }
}
