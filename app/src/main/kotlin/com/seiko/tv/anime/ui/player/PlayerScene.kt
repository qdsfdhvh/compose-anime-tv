package com.seiko.tv.anime.ui.player

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.seiko.compose.focuskit.TvControllerKey
import com.seiko.compose.focuskit.handleTvKey
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.component.foundation.TvSelectDialog
import com.seiko.tv.anime.component.foundation.TvVideoPlayer
import com.seiko.tv.anime.di.assisted.assistedViewModel

@Composable
fun PlayerScene(episode: String) {
  // 全屏
  val systemUiController = rememberSystemUiController()
  DisposableEffect(Unit) {
    systemUiController.isStatusBarVisible = false
    onDispose {
      systemUiController.isStatusBarVisible = true
    }
  }

  val navController = LocalAppNavigator.current
  var openDialog by remember { mutableStateOf(false) }
  val focusRequester = remember { FocusRequester() }

  val viewModel = assistedViewModel<PlayerViewModel.AssistedFactory, PlayerViewModel> { factory ->
    factory.create(episode)
  }
  val video by viewModel.video.collectAsState()
  Box(
    modifier = Modifier
      .handleTvKey(TvControllerKey.Back) {
        if (!openDialog) {
          openDialog = true
        }
        true
      }
      .focusRequester(focusRequester)
      .focusTarget(),
  ) {
    TvVideoPlayer(url = video.playUrl)

    if (openDialog) {
      TvSelectDialog(
        text = "是否退出播放？",
        onCenterClick = {
          openDialog = false
          navController.pop()
        },
        onCancelClick = {
          openDialog = false
        },
      )
    }
  }

  LaunchedEffect(Unit) {
    focusRequester.requestFocus()
  }
}