package com.seiko.tv.anime.navigation

import androidx.navigation.NavHostController

class AppNavigator(private val navController: NavHostController) {

  fun push(route: String) {
    navController.navigate(route)
  }

  fun pop() {
    navController.popBackStack()
  }
}