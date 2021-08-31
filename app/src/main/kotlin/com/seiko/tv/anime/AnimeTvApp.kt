package com.seiko.tv.anime

import android.app.Application
import android.util.Log
import com.seiko.compose.focuskit.TvLogger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AnimeTvApp : Application() {
  override fun onCreate() {
    super.onCreate()
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
}
