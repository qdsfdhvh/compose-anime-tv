package com.seiko.tv.anime.di.module

import com.seiko.compose.player.ExoVideoPlayerFactory
import org.koin.core.module.Module

actual fun Module.setupVideoPlayerFactory() {
  single { ExoVideoPlayerFactory(get()) }
}
