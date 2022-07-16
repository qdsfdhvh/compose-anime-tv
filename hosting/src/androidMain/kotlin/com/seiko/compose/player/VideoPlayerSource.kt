package com.seiko.compose.player

import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.RawResourceDataSource

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
