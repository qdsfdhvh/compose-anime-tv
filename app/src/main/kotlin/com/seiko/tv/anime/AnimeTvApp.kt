package com.seiko.tv.anime

import android.app.Application
import android.content.Context
import android.util.Log
import com.seiko.compose.focuskit.TvLogger
import com.seiko.tv.anime.util.starter.AppStartTaskDispatcher
import com.seiko.tv.anime.util.starter.BaseAppStarter
import dagger.hilt.android.HiltAndroidApp
import me.weishu.reflection.Reflection
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AnimeTvApp : Application() {

  @Inject
  lateinit var appStarters: Set<@JvmSuppressWildcards BaseAppStarter>

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    Reflection.unseal(base)
  }

  override fun onCreate() {
    super.onCreate()
    initLogger()
    initAppStarter()
  }

  private fun initLogger() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())

      TvLogger.setLogger(object : TvLogger {
        override var level: Int = Log.DEBUG
        override fun log(level: Int, msg: String?, throwable: Throwable?) {
          if (msg != null) {
            Timber.tag("Focuskit").log(level, msg)
          }
          if (throwable != null) {
            Timber.tag("Focuskit").log(level, msg, throwable)
          }
        }
      })
    }
  }

  private fun initAppStarter() {
    AppStartTaskDispatcher.Builder()
      .addAppStartTasks(appStarters)
      .setShowLog(BuildConfig.DEBUG)
      .build()
      .start()
  }
}
