package com.seiko.compose.player.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.Player
import com.seiko.compose.player.PlaybackState

@Composable
fun PlayToggleButton(
  modifier: Modifier = Modifier,
  isPlaying: Boolean,
  playbackState: PlaybackState
) {
  IconButton(
    modifier = modifier,
    onClick = { /* nothing to do */ }
  ) {
    when (playbackState) {
      PlaybackState.STATE_READY -> {
        if (isPlaying) {
          ShadowedIcon(icon = Icons.Filled.Pause)
        } else {
          ShadowedIcon(icon = Icons.Filled.PlayArrow)
        }
      }
      PlaybackState.STATE_ENDED -> {
        ShadowedIcon(icon = Icons.Filled.Restore)
      }
      PlaybackState.STATE_BUFFERING -> {
        CircularProgressIndicator()
      }
      PlaybackState.STATE_IDLE -> {
      }
    }
  }
}

@Composable
fun ShadowedIcon(
  icon: ImageVector,
  modifier: Modifier = Modifier,
  iconSize: Dp = 48.dp
) {
  Box(modifier = modifier) {
    Icon(
      imageVector = icon,
      tint = Color.White.copy(alpha = 0.3f),
      modifier = Modifier
        .size(iconSize)
        .offset(2.dp, 2.dp)
        .then(modifier),
      contentDescription = null
    )
    Icon(
      imageVector = icon,
      tint = Color.White,
      modifier = Modifier.size(iconSize),
      contentDescription = null
    )
  }
}

@Preview
@Composable
fun PlayToggleButtonPreview() {
  Column {
    Row {
      PlayToggleButton(isPlaying = true, playbackState = PlaybackState.STATE_READY)
      PlayToggleButton(isPlaying = false, playbackState = PlaybackState.STATE_READY)
    }
    Row {
      PlayToggleButton(isPlaying = false, playbackState = PlaybackState.STATE_ENDED)
      PlayToggleButton(isPlaying = false, playbackState = PlaybackState.STATE_BUFFERING)
    }
  }
}
