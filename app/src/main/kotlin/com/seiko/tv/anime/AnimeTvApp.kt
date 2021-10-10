package com.seiko.tv.anime

import android.app.Application
import android.content.Context
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
