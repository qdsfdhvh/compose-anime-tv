package com.seiko.compose.player

enum class VideoSeekDirection {
  NONE,
  Rewind,
  Forward;

  val isSeeking: Boolean
    get() = this != NONE
}
