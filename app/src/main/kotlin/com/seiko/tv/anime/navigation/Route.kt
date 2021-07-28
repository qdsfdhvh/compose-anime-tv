package com.seiko.tv.anime.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

val initialRoute = Route.Home.route

sealed class Route(val route: String) {
  open val arguments: List<NamedNavArgument> get() = emptyList()
  open val deepLinks: List<NavDeepLink> get() = emptyList()

  object Home : Route("home")

  object Detail : Route("detail/{animeId}") {
    operator fun invoke(animeId: Long) = "detail/$animeId"
    override val arguments = listOf(longArgument("animeId"))
    fun getId(entry: NavBackStackEntry): Long = entry.getString("animeId")?.toLongOrNull() ?: 0
  }
}

private fun stringArgument(name: String) = navArgument(name) { NavType.StringType }
private fun intArgument(name: String) = navArgument(name) { NavType.IntType }
private fun longArgument(name: String) = navArgument(name) { NavType.LongType }

private fun NavBackStackEntry.getString(key: String) = arguments?.getString(key)
private fun NavBackStackEntry.getInt(key: String) = arguments?.getInt(key)
private fun NavBackStackEntry.getLong(key: String) = arguments?.getLong(key)
