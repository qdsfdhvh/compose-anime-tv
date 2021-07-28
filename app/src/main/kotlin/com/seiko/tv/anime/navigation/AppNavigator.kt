package com.seiko.tv.anime.navigation

import androidx.navigation.NavHostController

class AppNavigator(
  private val navController: NavHostController
) {

  fun navigate(route: String) {
    navController.navigate(route)
  }

  fun popBackStack() {
    navController.popBackStack()
  }
}