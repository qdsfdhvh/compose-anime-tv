package com.seiko.tv.anime.di.module

import org.koin.core.module.Module
import org.koin.dsl.module

val playerModule = module {
  setupVideoPlayerFactory()
}

expect fun Module.setupVideoPlayerFactory()
