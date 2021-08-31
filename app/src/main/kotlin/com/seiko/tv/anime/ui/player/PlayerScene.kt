package com.seiko.tv.anime.ui.player

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.seiko.compose.focuskit.TvKeyEvent
import com.seiko.compose.focuskit.handleTvKey
import com.seiko.compose.player.TvVideoPlayer
import com.seiko.compose.player.rememberPlayer
import com.seiko.compose.player.rememberVideoPlayerController
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.component.foundation.TvSelectDialog
import com.seiko.tv.anime.util.video.HlsVideoPlayerFactory

@Composable
fun PlayerScene(episode: String) {
  val viewModel = playerViewModel(episode)
  val source by viewModel.video.collectAsState()

  if (source == null) return

  val player = rememberPlayer(source!!, HlsVideoPlayerFactory)
  val controller = rememberVideoPlayerController(player)

  var openDialog by remember { mutableStateOf(false) }
  var isPlaying by remember(source) { mutableStateOf(false) }

  fun savePlayState() {
    isPlaying = controller.isPlaying
  }

  fun restorePlayState() {
    if (isPlaying) {
      controller.play()
    }
  }

  Box(
    modifier = Modifier
      .handleTvKey(TvKeyEvent.Back) {
        if (!openDialog) {
          openDialog = true
          savePlayState()
          player.pause()
        }
        true
      }
  ) {
    TvVideoPlayer(
      player = player,
      controller = controller
    )

    if (openDialog) {
      val navController = LocalAppNavigator.current
      TvSelectDialog(
        text = "是否退出播放？",
        onCenterClick = {
          openDialog = false
          navController.pop()
        },
        onCancelClick = {
          openDialog = false
          restorePlayState()
        },
      )
    }
  }
}
