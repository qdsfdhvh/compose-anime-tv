package com.seiko.tv.anime.ui.composer.screener

import android.view.Choreographer
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.pow

object FpsSmallScreener : SmallScreener {

  @Composable
  override fun BoxScope.Show() {
    val fps by remember { getFpsFlow() }.collectAsState(0)
    Text(
      text = "${fps}fps",
      color = Color.Red,
      modifier = Modifier
        .align(Alignment.TopStart)
        .padding(10.dp),
    )
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
}

private const val FPS_MAX_DEFAULT = 60
private val FRAME_INTERVAL_NANOS = 10.0.pow(9.0) / FPS_MAX_DEFAULT
