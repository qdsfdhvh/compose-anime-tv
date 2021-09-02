package com.seiko.tv.anime.util

import android.view.Choreographer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlin.math.pow

object FpsHelper {

  private const val FPS_MAX_DEFAULT = 60
  private val FRAME_INTERVAL_NANOS = 10.0.pow(9.0) / FPS_MAX_DEFAULT

  @OptIn(ExperimentalCoroutinesApi::class)
  fun getFlow(scope: CoroutineScope): StateFlow<Int> {
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
      awaitClose { Choreographer.getInstance().removeFrameCallback(frameCallback) }
    }.stateIn(scope, SharingStarted.Lazily, 0)
  }
}
