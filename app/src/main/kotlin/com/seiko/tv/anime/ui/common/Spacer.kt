package com.seiko.tv.anime.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
@Suppress("NOTHING_TO_INLINE")
inline fun SpacerWidth(width: Dp) {
  Spacer(modifier = Modifier.width(width))
}

@Composable
@Suppress("NOTHING_TO_INLINE")
inline fun SpacerHeight(height: Dp) {
  Spacer(modifier = Modifier.height(height))
}
