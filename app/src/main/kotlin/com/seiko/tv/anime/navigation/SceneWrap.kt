package com.seiko.tv.anime.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive

@Composable
fun SceneWrap(content: @Composable () -> Unit) {

  var debugInfo by remember { mutableStateOf("") }

  Box(modifier = Modifier.fillMaxSize()) {
    content()

    Text(
      text = debugInfo,
      color = Color.Red,
      modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(10.dp),
    )
  }

  LaunchedEffect(Unit) {
    var startTime = withFrameNanos { it }
    while (isActive) {
      withFrameNanos { frameTime ->
        debugInfo = "%.4fms".format((frameTime - startTime).toFloat().div(1000_1000))
        startTime = frameTime
      }
    }
  }
}