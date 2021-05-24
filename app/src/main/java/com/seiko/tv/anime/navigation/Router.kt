package com.seiko.tv.anime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
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
  NavHost(navController, startDestination = initialRoute) {
    composable(Route.Home) { HomeScene() }
    composable(Route.Detail) { DetailScene(Route.Detail.getId(it)) }
  }
}

private fun NavGraphBuilder.composable(
  route: Route,
  content: @Composable (NavBackStackEntry) -> Unit
) {
  composable(route.route, route.arguments, route.deepLinks, content)
}
