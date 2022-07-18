package com.seiko.compose.player

interface VideoPlayerFactory {
  fun createPlayer(source: VideoPlayerSource): Player
}
