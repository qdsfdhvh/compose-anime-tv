package com.seiko.tv.anime.util

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier

actual fun Modifier.statusBarsPadding(): Modifier {
  return statusBarsPadding()
}
