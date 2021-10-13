package com.seiko.tv.anime.util

import android.app.Activity
import androidx.core.app.ActivityCompat

interface DoubleBackPressed {
  fun Activity.onDoubleBackPressed()
}

private const val CLICK_TIME = 2000L

class DoubleBackPressedDelegate : DoubleBackPressed {

  private var lastClickTime: Long = 0

  override fun Activity.onDoubleBackPressed() {
    val current = System.currentTimeMillis()
    if (current - lastClickTime > CLICK_TIME) {
      lastClickTime = current
      ToastUtils.showToast(this, "再按一次退出")
    } else {
      ActivityCompat.finishAfterTransition(this)
    }
  }
}
