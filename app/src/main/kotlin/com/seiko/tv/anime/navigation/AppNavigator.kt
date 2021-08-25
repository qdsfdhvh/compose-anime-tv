package com.seiko.tv.anime.navigation

import androidx.navigation.NavHostController

class AppNavigator(private val navController: NavHostController) {

  fun push(route: String): Boolean {
    navController.navigate(route)
    return true
  }

  @OptIn(ExperimentalStdlibApi::class)
  fun pop(): Boolean {
    // navGraph + backStack
    if (navController.backQueue.size > 2) {
      return navController.popBackStack()
    }
    return false
  }
}