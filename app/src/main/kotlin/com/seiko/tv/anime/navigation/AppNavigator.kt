package com.seiko.tv.anime.navigation

import androidx.navigation.NavHostController
import androidx.navigation.navOptions

class AppNavigator(private val navController: NavHostController) {

  fun push(router: Router, isSingleTop: Boolean = false): Boolean {
    return push(router.route, isSingleTop)
  }

  fun push(route: String, isSingleTop: Boolean = false): Boolean {
    navController.navigate(route, navOptions {
      launchSingleTop = isSingleTop
    })
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
