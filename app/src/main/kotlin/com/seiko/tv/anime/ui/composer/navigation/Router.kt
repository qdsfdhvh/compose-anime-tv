package com.seiko.tv.anime.ui.composer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seiko.tv.anime.ui.detail.DetailScene
import com.seiko.tv.anime.ui.favorite.FavoriteScene
import com.seiko.tv.anime.ui.feed.FeedScene
import com.seiko.tv.anime.ui.home.HomeScene
import com.seiko.tv.anime.ui.player.PlayerScene
import com.seiko.tv.anime.util.decodeUrl
import com.seiko.tv.anime.util.encodeUrl

@Composable
fun Router(appNavigator: AppNavigator) {
  NavHost(appNavigator.navController, startDestination = initialRoute) {
    composable(Router.Home) { HomeScene() }
    composable(Router.Feed) { FeedScene() }
    composable(Router.Detail) {
      DetailScene(Router.Detail.getUri(it))
    }
    composable(Router.Player) {
      PlayerScene(Router.Player.getUri(it))
    }
    composable(Router.Favorite) { FavoriteScene() }
  }
}

private fun NavGraphBuilder.composable(
  router: Router,
  arguments: List<NamedNavArgument> = emptyList(),
  deepLinks: List<NavDeepLink> = emptyList(),
  content: @Composable (NavBackStackEntry) -> Unit
) {
  composable(router.route, arguments, deepLinks, content)
}

sealed class Router(val route: String) {

  object Home : Router("/home")

  object Feed : Router("/feed")

  object Detail : Router("/detail?uri={uri}") {
    fun getUri(entry: NavBackStackEntry): String {
      return entry.arguments?.getString("uri")?.decodeUrl() ?: ""
    }

    operator fun invoke(uri: String): String {
      return "/detail?uri=${uri.encodeUrl()}"
    }
  }

  object Player : Router("/player?uri={uri}") {
    fun getUri(entry: NavBackStackEntry): String {
      return entry.arguments?.getString("uri")?.decodeUrl() ?: ""
    }

    operator fun invoke(uri: String): String {
      return "/player?uri=${uri.encodeUrl()}"
    }
  }

  object Favorite : Router("/favorite")
}

private val initialRoute = Router.Home.route
