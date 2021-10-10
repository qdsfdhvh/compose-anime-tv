package com.seiko.tv.anime.ui.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.seiko.compose.focuskit.handleBack
import com.seiko.compose.player.TvVideoPlayer
import com.seiko.compose.player.rememberPlayer
import com.seiko.compose.player.rememberVideoPlayerController
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.ui.common.foundation.LoadingState
import com.seiko.tv.anime.ui.common.foundation.TvSelectDialog
import com.seiko.tv.anime.util.video.HlsVideoPlayerFactory

@Composable
fun PlayerScene(episode: String) {
  val viewModel = playerViewModel(episode)
  val source by viewModel.video.collectAsState()

  if (source == null) {
    LoadingState()
    return
  }

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
      .handleBack {
        if (!openDialog) {
          openDialog = true
          savePlayState()
          player.pause()
        }
      }
  ) {
    TvVideoPlayer(
      player = player,
      controller = controller,
      modifier = Modifier.fillMaxSize()
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
