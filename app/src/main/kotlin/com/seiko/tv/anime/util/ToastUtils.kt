package com.seiko.tv.anime.util

import android.content.Context
import android.widget.Toast

object ToastUtils {
  fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
  }
}
