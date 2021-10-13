package com.seiko.tv.anime.ui.composer.navigation

import android.app.Activity
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.navOptions

interface AppNavigator {
  fun push(router: Router, isSingleTop: Boolean = false) = push(router.route, isSingleTop)
  fun push(route: String, isSingleTop: Boolean = false): Boolean
  fun pop(): Boolean

  val navController: NavHostController
}

class AppNavigatorImpl(activity: Activity) : AppNavigator {

  override val navController = NavHostController(activity).apply {
    navigatorProvider.addNavigator(ComposeNavigator())
    navigatorProvider.addNavigator(DialogNavigator())
  }

  override fun push(route: String, isSingleTop: Boolean): Boolean {
    navController.navigate(
      route,
      navOptions {
        launchSingleTop = isSingleTop
      }
    )
    return true
  }

  @OptIn(ExperimentalStdlibApi::class)
  override fun pop(): Boolean {
    // navGraph + backStack
    if (navController.backQueue.size > 2) {
      return navController.popBackStack()
    }
    return false
  }
}
