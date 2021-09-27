package com.seiko.tv.anime.util

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.drawscope.ContentDrawScope

/**
 * 无波纹点击
 */
object NoRippleIndication : Indication {
  private object NoIndicationInstance : IndicationInstance {
    override fun ContentDrawScope.drawIndication() {
      drawContent()
    }
  }

  @Composable
  override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
    return NoIndicationInstance
  }
}
