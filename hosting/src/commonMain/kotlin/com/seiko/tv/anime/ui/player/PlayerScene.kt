package com.seiko.tv.anime.ui.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.seiko.compose.player.TvVideoPlayer
import com.seiko.compose.player.VideoPlayerFactory
import com.seiko.compose.player.VideoPlayerSource
import com.seiko.compose.player.rememberVideoPlayerController
import com.seiko.tv.anime.ui.widget.LoadingIndicator
import com.seiko.tv.anime.ui.widget.TvSelectDialog
import moe.tlaster.koin.get
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.rememberPresenter

@Composable
fun PlayerScene(
  navigator: Navigator,
  uri: String
) {
  val stateFlow = rememberPresenter { PlayerPresenter(uri) }
  when (val state = stateFlow.collectAsState().value) {
    PlayerState.Loading -> {
      LoadingIndicator()
    }
    is PlayerState.Success -> {
      PlayerScene(
        source = state.source,
        onBack = {
          navigator.popBackStack()
        }
      )
    }
  }
}

@Composable
fun PlayerScene(
  source: VideoPlayerSource,
  onBack: () -> Unit,
) {
  val playerFactory: VideoPlayerFactory = get()
  val player = remember(source) {
    playerFactory.createPlayer(source)
  }
  val scope = rememberCoroutineScope()
  val controller = rememberVideoPlayerController(player, scope)

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

  BackHandler {
    if (!openDialog) {
      openDialog = true
      savePlayState()
      controller.pause()
    }
  }

  Box {
    TvVideoPlayer(
      player = player,
      controller = controller,
      modifier = Modifier.fillMaxSize()
    )

    if (openDialog) {
      TvSelectDialog(
        text = "是否退出播放？",
        onCenterClick = {
          openDialog = false
          onBack.invoke()
        },
        onCancelClick = {
          openDialog = false
          restorePlayState()
        }
      )
    }
  }
}
