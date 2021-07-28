package com.seiko.tv.anime.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.navigation.AppNavigator

@Composable
fun DetailScene(id: Long) {
  val navigator: AppNavigator = LocalAppNavigator.current

  Box(modifier = Modifier.fillMaxSize()) {
    Button(
      onClick = { navigator.popBackStack() },
      modifier = Modifier.align(Alignment.Center)
    ) {
      Text(text = "go back $id")
    }
  }
}