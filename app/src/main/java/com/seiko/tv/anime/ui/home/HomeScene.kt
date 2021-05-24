package com.seiko.tv.anime.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.navigation.AppNavigator
import com.seiko.tv.anime.navigation.Route
import kotlin.random.Random

@Composable
fun HomeScene() {
  val navigator: AppNavigator = LocalAppNavigator.current

  Box(modifier = Modifier.fillMaxSize()) {
    Button(
      onClick = {
        navigator.navigate(Route.Detail(Random.nextLong(1000)))
      },
      modifier = Modifier.align(Alignment.Center)
    ) {
      Text("go to detail")
    }
  }
}