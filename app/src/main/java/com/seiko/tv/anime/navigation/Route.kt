package com.seiko.tv.anime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*
import com.seiko.tv.anime.ui.detail.DetailScene
import com.seiko.tv.anime.ui.home.HomeScene

const val initialRoute = Route.Home

object Route {
  const val Home = "home"

  object Detail : Provide {
    private const val id = "animeId"

    override val route = "detail/{${id}}"
    override val arguments = listOf(navArgument(id) { NavType.LongType })

    operator fun invoke(id: Long) = "detail/$id"

    fun getId(entry: NavBackStackEntry): Long {
      return entry.arguments?.getString(id)?.toLongOrNull() ?: 0
    }
  }
}

private interface Provide {
  val route: String
  val arguments: List<NamedNavArgument>
  val deepLinks: List<NavDeepLink> get() = emptyList()
}

private fun NavGraphBuilder.composable(
  provide: Provide,
  content: @Composable (NavBackStackEntry) -> Unit
) {
  composable(provide.route, provide.arguments, provide.deepLinks, content)
}

@Composable
fun Router(navController: NavHostController = rememberNavController()) {
  NavHost(navController, startDestination = initialRoute) {
    composable(Route.Home) { HomeScene() }
    composable(Route.Detail) { DetailScene(Route.Detail.getId(it)) }
  }
}