package com.seiko.tv.anime.ui.composer.collector

import android.view.Choreographer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.tv.anime.di.scope.CollectScreenComponentQualifier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.pow

@OptIn(ExperimentalUnitApi::class)
@CollectCompose(CollectScreenComponentQualifier::class)
@Composable
fun BoxScope.FpsScreenComponent() {
  val fps by remember { getFpsFlow() }.collectAsState(0)
  Box(
    modifier = Modifier
      .align(Alignment.TopStart)
      .statusBarsPadding()
      .padding(start = 10.dp)
      .size(40.dp)
      .background(Color.Gray.copy(alpha = 0.8f), CircleShape),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = "${fps}fps",
      color = Color.White,
      style = MaterialTheme.typography.caption
    )
  }
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun getFpsFlow(): Flow<Int> {
  return callbackFlow {
    var lastFrameTimeNanos = 0L

    val frameCallback = object : Choreographer.FrameCallback {
      override fun doFrame(frameTimeNanos: Long) {
        if (lastFrameTimeNanos != 0L) {
          val diffFrameCoast = frameTimeNanos - lastFrameTimeNanos
          val skipped = (diffFrameCoast - FRAME_INTERVAL_NANOS) / FRAME_INTERVAL_NANOS
          val fps = (FPS_MAX_DEFAULT - skipped.toInt()).coerceAtLeast(0)
          trySend(fps)
        }

        lastFrameTimeNanos = frameTimeNanos
        Choreographer.getInstance().postFrameCallback(this)
      }
    }

    Choreographer.getInstance().postFrameCallback(frameCallback)
    awaitClose {
      Choreographer.getInstance().removeFrameCallback(frameCallback)
    }
  }
}

private const val FPS_MAX_DEFAULT = 60
private val FRAME_INTERVAL_NANOS = 10.0.pow(9.0) / FPS_MAX_DEFAULT
