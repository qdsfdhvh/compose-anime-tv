package com.seiko.tv.anime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seiko.tv.anime.ui.detail.DetailScene
import com.seiko.tv.anime.ui.home.HomeScene

@Composable
fun Router(
  navController: NavHostController = rememberNavController()
) {
  NavHost(navController, startDestination = "/home") {
    composable("/home") { HomeScene() }
    composable("/show/{animeId}.html") {
      val animeId = it.arguments?.getString("animeId")?.toIntOrNull() ?: 0
      DetailScene(animeId)
    }
  }
}
