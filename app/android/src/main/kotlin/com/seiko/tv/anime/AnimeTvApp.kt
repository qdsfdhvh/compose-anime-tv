package com.seiko.tv.anime

import android.app.Application
import com.seiko.tv.anime.di.appModules
import com.seiko.tv.anime.di.commonModules
import com.seiko.tv.anime.di.serviceModules
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AnimeTvApp : Application() {
  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Napier.base(DebugAntilog())
    }
    startKoin {
      androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
      androidContext(this@AnimeTvApp)
      modules(commonModules + serviceModules + appModules)
    }
  }
}
