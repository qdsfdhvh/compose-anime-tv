package com.seiko.tv.anime.navigation

import androidx.navigation.NavHostController

class AppNavigator(private val navController: NavHostController) {

  fun push(route: String): Boolean {
    navController.navigate(route)
    return true
  }

  fun pop(): Boolean {
    return navController.popBackStack()
  }
}