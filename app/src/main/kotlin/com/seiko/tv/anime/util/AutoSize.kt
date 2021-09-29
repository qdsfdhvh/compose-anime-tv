package com.seiko.tv.anime.util

import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.unit.Density

/**
 * 屏幕适配
 */
fun autoSizeDensity(context: Context, designWidthInDp: Int): Density =
  with(context.resources) {
    val isVertical = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val scale = displayMetrics.run {
      val sizeInDp = if (isVertical) widthPixels else heightPixels
      sizeInDp.toFloat() / density / designWidthInDp
    }

    Density(
      density = displayMetrics.density * scale,
      fontScale = configuration.fontScale * scale
    )
  }
