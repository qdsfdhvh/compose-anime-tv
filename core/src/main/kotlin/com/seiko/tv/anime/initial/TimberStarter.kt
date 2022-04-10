package com.seiko.tv.anime.initial

import com.seiko.tv.anime.core.BuildConfig
import com.seiko.tv.anime.util.starter.BaseAppStarter
import timber.log.Timber
import javax.inject.Inject

class TimberStarter @Inject constructor() : BaseAppStarter() {
  override fun run() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}
