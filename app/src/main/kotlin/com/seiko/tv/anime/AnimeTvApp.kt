package com.seiko.tv.anime

import android.app.Application
import android.content.Context
import com.seiko.tv.anime.di.appModules
import com.seiko.tv.anime.di.commonModules
import com.seiko.tv.anime.di.serviceModules
import me.weishu.reflection.Reflection
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class AnimeTvApp : Application() {

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    Reflection.unseal(base)
  }

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    startKoin {
      androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
      androidContext(this@AnimeTvApp)
      modules(commonModules + serviceModules + appModules)
    }
  }
}
