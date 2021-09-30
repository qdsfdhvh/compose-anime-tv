package com.seiko.tv.anime.ui.composer.screener

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable

/**
 * 收集带有SmallScreen的Composable函数，自动生成Dagger Module
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class SmallScreen

interface SmallScreenWrap {
  @Composable
  fun BoxScope.Show()
}

@Composable
fun BoxScope.Show(wraps: Collection<SmallScreenWrap>) {
  if (wraps.isNotEmpty()) {
    wraps.forEach { wrap ->
      wrap.run { this@Show.Show() }
    }
  }
}
