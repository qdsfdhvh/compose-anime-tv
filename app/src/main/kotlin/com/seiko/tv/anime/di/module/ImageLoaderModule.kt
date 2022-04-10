package com.seiko.tv.anime.di.module

import coil.ImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val imageLoaderModule = module {
  single {
    ImageLoader.Builder(androidContext())
      .availableMemoryPercentage(0.25)
      .crossfade(true)
      .build()
  }
}