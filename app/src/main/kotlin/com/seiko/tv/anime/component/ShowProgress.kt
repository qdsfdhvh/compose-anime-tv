package com.seiko.tv.anime.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ShowProgress(
  background: Color = MaterialTheme.colors.background
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(background),
  ) {
    CircularProgressIndicator(
      modifier = Modifier.align(Alignment.Center)
    )
  }
}
