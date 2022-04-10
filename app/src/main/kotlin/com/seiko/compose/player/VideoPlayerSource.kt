package com.seiko.compose.player

import android.net.Uri
import androidx.annotation.RawRes
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.RawResourceDataSource

sealed class VideoPlayerSource {

  data class Network(
    val url: String,
    val headers: Map<String, String> = emptyMap()
  ) : VideoPlayerSource()

  data class Raw(
    @RawRes val resId: Int
  ) : VideoPlayerSource()
}

fun VideoPlayerSource.toMediaItem(): MediaItem {
  return when (this) {
    is VideoPlayerSource.Network -> {
      MediaItem.fromUri(Uri.parse(url))
    }
    is VideoPlayerSource.Raw -> {
      MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(resId))
    }
  }
}
