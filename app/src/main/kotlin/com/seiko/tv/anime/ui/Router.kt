package com.seiko.tv.anime.ui

import androidx.compose.runtime.Composable
import com.seiko.tv.anime.ui.detail.DetailScene
import com.seiko.tv.anime.ui.favorite.FavoriteScene
import com.seiko.tv.anime.ui.feed.FeedScene
import com.seiko.tv.anime.ui.home.HomeScene
import com.seiko.tv.anime.ui.player.PlayerScene
import com.seiko.tv.anime.ui.tag.TagScene
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.NavController
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.RouteBuilder

@Composable
fun Router(navController: NavController) {
  NavHost(navController, initialRoute = initialRoute) {
    scene(Router.Home) {
      HomeScene(navController = navController)
    }
    scene(Router.Feed) {
      FeedScene(navController = navController)
    }
    scene(Router.Detail) {
      DetailScene(
        navController = navController,
        uri = Router.Detail.getUri(it)
      )
    }
    scene(Router.Player) {
      PlayerScene(
        navController = navController,
        uri = Router.Player.getUri(it)
      )
    }
    scene(Router.Favorite) {
      FavoriteScene(navController = navController)
    }
    scene(Router.TagPage) {
      TagScene(
        navController = navController,
        uri = Router.TagPage.getUri(it)
      )
    }
  }
}

private fun RouteBuilder.scene(
  router: Router,
  deepLinks: List<String> = emptyList(),
  content: @Composable (BackStackEntry) -> Unit
) {
  scene(
    route = router.route,
    deepLinks = deepLinks,
    content = content
  )
}

sealed class Router(val route: String) {

  object Home : Router("/home")

  object Feed : Router("/feed")

  object Detail : Router("/detail") {
    fun getUri(entry: BackStackEntry): String {
      return entry.query("uri", "")
    }

    operator fun invoke(uri: String): String {
      return "/detail?uri=$uri"
    }
  }

  object Player : Router("/player") {
    fun getUri(entry: BackStackEntry): String {
      return entry.query("uri", "")
    }

    operator fun invoke(uri: String): String {
      return "/player?uri=$uri"
    }
  }

  object Favorite : Router("/favorite")

  object TagPage : Router("/tagpage") {
    fun getUri(entry: BackStackEntry): String {
      return entry.query("uri", "")
    }

    operator fun invoke(uri: String): String {
      return "/tagpage?uri=$uri"
    }
  }
}

private val initialRoute = Router.Home.route
