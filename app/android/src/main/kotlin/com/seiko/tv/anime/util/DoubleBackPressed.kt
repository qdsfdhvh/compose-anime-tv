package com.seiko.tv.anime.util

import android.app.Activity
import androidx.core.app.ActivityCompat

interface DoubleBackPressed {
  fun Activity.onDoubleBackPressed(): Boolean

  companion object : DoubleBackPressed {

    private var lastClickTime: Long = 0

    override fun Activity.onDoubleBackPressed(): Boolean {
      val current = System.currentTimeMillis()
      if (current - lastClickTime > CLICK_TIME) {
        lastClickTime = current
        ToastUtils.showToast("再按一次退出")
      } else {
        ActivityCompat.finishAfterTransition(this)
      }
      return true
    }
  }
}

private const val CLICK_TIME = 2000L
