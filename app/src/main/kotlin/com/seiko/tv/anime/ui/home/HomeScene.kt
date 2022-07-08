package com.seiko.tv.anime.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import com.seiko.compose.focuskit.focusClick
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.common.SetSystemBarColor
import com.seiko.tv.anime.ui.common.foundation.RoundIcon
import moe.tlaster.koin.getViewModel
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScene(
  navigator: Navigator
) {
  SetSystemBarColor()

  val viewModel: HomeViewModel = getViewModel()
  val list by viewModel.list.collectAsState()

  Scaffold { innerPadding ->
    Box(Modifier.padding(innerPadding).fillMaxSize(), Alignment.Center) {
      var focusIndex by rememberSaveable { mutableStateOf(0) }
      Row {
        list.forEachIndexed { index, item ->
          val focusRequester = remember { FocusRequester() }
          var isFocused by remember { mutableStateOf(false) }

          RoundIcon(
            image = item.icon,
            name = item.name,
            isFocused = isFocused,
            modifier = Modifier
              .focusClick {
                focusRequester.requestFocus()
                when (item) {
                  HomeItem.Home -> navigator.navigate(Router.Feed.route)
                  HomeItem.Favorite -> navigator.navigate(Router.Favorite.route)
                  else -> Unit
                }
              }
              .onFocusChanged {
                isFocused = it.isFocused
                if (isFocused) focusIndex = index
              }
              .focusRequester(focusRequester)
              .focusTarget()
          )

          if (focusIndex == index) {
            SideEffect {
              focusRequester.requestFocus()
            }
          }
        }
      }
    }
  }
}
