package com.seiko.compose.player

sealed class VideoPlayerSource {

  data class Network(
    val url: String,
    val headers: Map<String, String> = emptyMap()
  ) : VideoPlayerSource()

  data class Raw(
    val resId: Int
  ) : VideoPlayerSource()
}
