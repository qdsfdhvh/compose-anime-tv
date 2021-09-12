package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.seiko.tv.anime.R

@Composable
fun LoadingState(backgroundColor: Color = MaterialTheme.colors.background) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(backgroundColor),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

@Composable
fun ErrorState(onRetry: () -> Unit = {}) {
  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    OutlinedButton(onClick = onRetry) {
      Text(
        text = stringResource(id = R.string.retry),
        style = TextStyle(color = MaterialTheme.colors.onPrimary, fontSize = 20.sp)
      )
    }
  }
}
