package com.seiko.tv.anime.util.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * 无波纹点击
 * https://stackoverflow.com/a/66839858/14299073
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier = composed {
  clickable(
    indication = null,
    interactionSource = remember { MutableInteractionSource() },
    onClick = onClick
  )
}