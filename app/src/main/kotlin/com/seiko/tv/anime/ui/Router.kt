package com.seiko.tv.anime.ui

import androidx.compose.runtime.Composable
import com.seiko.tv.anime.ui.detail.DetailScene
import com.seiko.tv.anime.ui.favorite.FavoriteScene
import com.seiko.tv.anime.ui.feed.FeedScene
import com.seiko.tv.anime.ui.home.HomeScene
import com.seiko.tv.anime.ui.player.PlayerScene
import com.seiko.tv.anime.ui.tag.TagScene
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.query

@Composable
fun Router(navigator: Navigator) {
  NavHost(navigator = navigator, initialRoute = initialRoute) {
    scene(Router.Home) {
      HomeScene(navigator = navigator)
    }
    scene(Router.Feed) {
      FeedScene(navigator = navigator)
    }
    scene(Router.Detail) {
      DetailScene(
        navigator = navigator,
        uri = Router.Detail.getUri(it)
      )
    }
    scene(Router.Player) {
      PlayerScene(
        navigator = navigator,
        uri = Router.Player.getUri(it)
      )
    }
    scene(Router.Favorite) {
      FavoriteScene(navigator = navigator)
    }
    scene(Router.TagPage) {
      TagScene(
        navigator = navigator,
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
      return entry.query("uri", "").orEmpty()
    }

    operator fun invoke(uri: String): String {
      return "/detail?uri=$uri"
    }
  }

  object Player : Router("/player") {
    fun getUri(entry: BackStackEntry): String {
      return entry.query("uri", "").orEmpty()
    }

    operator fun invoke(uri: String): String {
      return "/player?uri=$uri"
    }
  }

  object Favorite : Router("/favorite")

  object TagPage : Router("/tagpage") {
    fun getUri(entry: BackStackEntry): String {
      return entry.query("uri", "").orEmpty()
    }

    operator fun invoke(uri: String): String {
      return "/tagpage?uri=$uri"
    }
  }
}

private val initialRoute = Router.Home.route
