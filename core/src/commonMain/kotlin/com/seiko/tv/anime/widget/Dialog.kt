package com.seiko.tv.anime.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

private val LocalDialogHost = staticCompositionLocalOf<ContentHolder> { error("No DialogHost") }

private class ContentHolder {
  private var _content = mutableStateOf<@Composable (() -> Unit)?>(null)
  val content: @Composable (() -> Unit)? by _content
  fun setContent(content: @Composable (() -> Unit)?) {
    _content.value = content
  }
}

@Composable
fun ProvideDialogHost(
  content: @Composable () -> Unit
) {
  Box {
    val dialogHost = remember {
      ContentHolder()
    }
    CompositionLocalProvider(
      LocalDialogHost provides dialogHost
    ) {
      content.invoke()
    }
    dialogHost.content?.invoke()
  }
}

// FIXME: Just an ugly workaround
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogImpl(
  onDismissRequest: () -> Unit,
  content: @Composable () -> Unit
) {
  val dialogHost = LocalDialogHost.current
  DisposableEffect(Unit) {
    onDispose {
      dialogHost.setContent(null)
    }
  }
  dialogHost.setContent {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(DrawerDefaults.ScrimColor)
        .clickable(
          onClick = {
            onDismissRequest()
          },
          interactionSource = remember { MutableInteractionSource() },
          indication = null,
        ),
      contentAlignment = Alignment.Center,
    ) {
      content.invoke()
    }
  }
}
