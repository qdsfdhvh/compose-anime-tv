package com.seiko.tv.anime.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

object ToastUtils {
  fun showToast(msg: String?) {
    if (msg == null) return
    channel.trySend(msg)
  }
}

private val channel = Channel<String>(1)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BoxScope.ToastScreenComponent() {

  var isShown by remember { mutableStateOf(false) }
  var showMsg by remember { mutableStateOf("") }

  LaunchedEffect(Unit) {
    channel.receiveAsFlow().collect {
      showMsg = it
      isShown = true
    }
  }

  AnimatedVisibility(
    visible = isShown,
    modifier = Modifier
      .padding(10.dp)
      .padding(bottom = 50.dp)
      .align(Alignment.BottomCenter),
    enter = fadeIn(),
    exit = fadeOut()
  ) {
    Text(
      text = showMsg,
      modifier = Modifier
        .shadow(1.dp, CircleShape)
        .background(MaterialTheme.colors.surface, CircleShape)
        .padding(horizontal = 20.dp, vertical = 10.dp)
    )
  }

  if (isShown) {
    LaunchedEffect(Unit) {
      delay(1500)
      isShown = false
    }
  }
}
