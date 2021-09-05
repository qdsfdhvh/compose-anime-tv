package com.seiko.tv.anime.util.starter

import android.annotation.SuppressLint
import android.os.Build
import android.os.StrictMode
import android.os.strictmode.UntaggedSocketViolation
import android.util.Log
import com.seiko.tv.anime.feature.analytics.BuildConfig
import java.util.concurrent.Executors
import javax.inject.Inject

class StrictModeStarter @Inject constructor() : BaseAppStarter() {
  @SuppressLint("LogNotTimber")
  override fun run() {
    if (!BuildConfig.DEBUG) {
      return
    }

    val penaltyListenerExecutor = Executors.newSingleThreadExecutor()

    StrictMode.setThreadPolicy(
      StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .apply {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            penaltyListener(penaltyListenerExecutor) {
              Log.w("StrictMode", it)
            }
          } else {
            penaltyLog()
          }
        }
        .build()
    )
    StrictMode.setVmPolicy(
      StrictMode.VmPolicy.Builder()
        .detectAll()
        .apply {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            penaltyListener(penaltyListenerExecutor) {
              if (it is UntaggedSocketViolation) {
                return@penaltyListener
              }
              Log.w("StrictMode", it)
            }
          } else {
            penaltyLog()
          }
        }
        .build()
    )
  }
}
