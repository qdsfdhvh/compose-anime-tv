package com.seiko.tv.anime.util

import android.os.Build
import android.os.Handler
import android.os.Looper

object Global {

  private val handler by lazy { Handler(Looper.getMainLooper()) }

  fun doOnIdle(runnable: Runnable) {
    when {
      Looper.myLooper() == Looper.getMainLooper() -> {
        Looper.myQueue().addIdleHandler {
          runnable.run()
          false
        }
      }
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
        Looper.getMainLooper().queue.addIdleHandler {
          runnable.run()
          false
        }
      }
      else -> {
        handler.post {
          Looper.myQueue().addIdleHandler {
            runnable.run()
            false
          }
        }
      }
    }
  }
}
