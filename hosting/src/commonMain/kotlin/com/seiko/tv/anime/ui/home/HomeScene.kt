package com.seiko.tv.anime.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.widget.LoadingIndicator
import com.seiko.tv.anime.ui.widget.RoundIcon
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.rememberPresenter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScene(
  navigator: Navigator,
) {
  val stateFlow = rememberPresenter { HomePresenter() }
  Scaffold { innerPadding ->
    when (val state = stateFlow.collectAsState().value) {
      is HomeState.Loading -> {
        LoadingIndicator(
          modifier = Modifier.padding(innerPadding),
        )
      }

      is HomeState.Idle -> {
        HomeScene(
          list = state.items,
          onItemClick = { item ->
            when (item) {
              HomeItem.Home -> navigator.navigate(Router.Feed.route)
              HomeItem.Favorite -> navigator.navigate(Router.Favorite.route)
              HomeItem.Setting -> Unit
            }
          },
          modifier = Modifier.padding(innerPadding),
        )
      }
    }
  }
}

@Composable
fun HomeScene(
  list: List<HomeItem>,
  onItemClick: (HomeItem) -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    var focusIndex by rememberSaveable { mutableStateOf(0) }
    val focusRequesters = remember(list.size) {
      Array(list.size) { FocusRequester() }
    }

    Row {
      list.forEachIndexed { index, item ->
        var isFocused by remember { mutableStateOf(false) }
        RoundIcon(
          image = item.icon,
          name = item.name,
          isFocused = isFocused,
          color = Color.Transparent,
          contentColor = MaterialTheme.colorScheme.onBackground,
          modifier = Modifier
            .clickable {
              focusRequesters[index].requestFocus()
              onItemClick.invoke(item)
            }
            .onFocusChanged {
              isFocused = it.isFocused
              if (isFocused) focusIndex = index
            }
            .focusProperties {
              left = focusRequesters.getOrNull(index - 1) ?: FocusRequester.Default
              right = focusRequesters.getOrNull(index + 1) ?: FocusRequester.Default
            }
            .focusRequester(focusRequesters[index])
            .focusTarget()
        )
        if (focusIndex == index) {
          SideEffect {
            focusRequesters[index].requestFocus()
          }
        }
      }
    }
  }
}
