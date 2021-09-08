package com.seiko.tv.anime.ui.favorite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seiko.tv.anime.ui.common.foundation.GroupItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScene() {
  val viewModel: FavoriteViewModel = hiltViewModel()

  val list = viewModel.favorites.collectAsLazyPagingItems()

  Surface(
    color = MaterialTheme.colors.background,
    modifier = Modifier.fillMaxSize(),
  ) {
    LazyVerticalGrid(cells = GridCells.Fixed(6)) {
      items(list) { anime ->
        val interactionSource = remember { MutableInteractionSource() }
        GroupItem(
          item = anime!!,
          isFocused = interactionSource.collectIsFocusedAsState().value,
          modifier = Modifier
            .focusable(interactionSource = interactionSource)
        )
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
fun <T : Any> LazyGridScope.items(
  items: LazyPagingItems<T>,
  itemContent: @Composable LazyGridScope.(value: T?) -> Unit
) {
  items(count = items.itemCount) { index ->
    itemContent(items[index])
  }
}
