package com.seiko.tv.anime.ui.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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

  val viewModel = assistedViewModel<PlayerViewModel.AssistedFactory, PlayerViewModel> { factory ->
    factory.create(episode)
  }
  val video by viewModel.video.collectAsState()
  TvVideoPlayer(url = video.playUrl)
}