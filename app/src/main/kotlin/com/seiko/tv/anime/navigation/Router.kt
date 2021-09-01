package com.seiko.tv.anime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.seiko.tv.anime.ui.detail.DetailScene
import com.seiko.tv.anime.ui.feed.FeedScene
import com.seiko.tv.anime.ui.home.HomeScene
import com.seiko.tv.anime.ui.player.PlayerScene

@Composable
fun Router(
  navController: NavHostController = rememberNavController()
) {
  NavHost(navController, startDestination = initialRoute) {
    composable(Router.Home) { HomeScene() }
    composable(Router.Feed) { FeedScene() }
    composable(Router.Detail, arguments = Router.Detail.arguments) {
      DetailScene(Router.Detail.getAnimeId(it))
    }
    composable(Router.Player) {
      PlayerScene(Router.Player.getEpisode(it))
    }
  }
}

private fun NavGraphBuilder.composable(
  router: Router,
  arguments: List<NamedNavArgument> = emptyList(),
  deepLinks: List<NavDeepLink> = emptyList(),
  content: @Composable (NavBackStackEntry) -> Unit
) {
  composable(router.route, arguments, deepLinks) {
    SceneWrap {
      content(it)
    }
  }
}

sealed class Router(val route: String) {

  object Home : Router("/home")

  object Feed : Router("/feed")

  object Detail : Router("/show/{animeId}.html") {
    val arguments = listOf(navArgument("animeId") { type = NavType.IntType })
    fun getAnimeId(entry: NavBackStackEntry): Int {
      return entry.arguments?.getInt("animeId") ?: 0
    }
  }

  object Player : Router("/v/{episode}.html") {
    fun getEpisode(entry: NavBackStackEntry): String {
      return entry.arguments?.getString("episode").orEmpty()
    }
  }
}

private val initialRoute = Router.Home.route
