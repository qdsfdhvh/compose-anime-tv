package com.seiko.tv.anime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seiko.tv.anime.ui.detail.DetailScene
import com.seiko.tv.anime.ui.feed.FeedScene
import com.seiko.tv.anime.ui.player.PlayerScene

@Composable
fun Router(
  navController: NavHostController = rememberNavController()
) {
  NavHost(navController, startDestination = "/feed") {
    composable("/feed") { FeedScene() }
    composable("/show/{animeId}.html") {
      val animeId = it.arguments?.getString("animeId")?.toIntOrNull() ?: 0
      DetailScene(animeId)
    }
    composable("/v/{episode}.html") {
      val episode = it.arguments?.getString("episode").orEmpty()
      PlayerScene(episode)
    }
  }
}
