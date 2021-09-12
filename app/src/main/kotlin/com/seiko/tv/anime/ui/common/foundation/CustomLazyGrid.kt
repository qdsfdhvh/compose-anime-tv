package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> LazyGridFor(
  pagingItems: LazyPagingItems<T>,
  nColumns: Int,
  modifier: Modifier = Modifier,
  title: String = "",
  state: LazyListState = rememberLazyListState(),
  itemContent: @Composable LazyItemScope.(T, Int) -> Unit
) {
  val rows = remember {
    derivedStateOf { (pagingItems.itemCount + nColumns - 1) / nColumns }
  }

  LazyColumn(
    modifier = modifier,
    state = state
  ) {
    if (title.isNotEmpty()) {
      item {
        Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = title,
            style = MaterialTheme.typography.h3
          )
        }
      }
    }

    items(rows.value) { rowIndex ->
      Row {
        for (columnIndex in 0 until nColumns) {
          val itemIndex = rowIndex * nColumns + columnIndex
          if (itemIndex < pagingItems.itemCount) {
            Box(
              modifier = Modifier.weight(1f, fill = true),
              contentAlignment = Alignment.Center
            ) {
              pagingItems[itemIndex]?.let { item ->
                itemContent(item, itemIndex)
              }
            }
          } else {
            Spacer(Modifier.weight(1f, fill = true))
          }
        }
      }
    }

    pagingItems.apply {
      when {
        loadState.refresh is LoadState.Loading -> {
          item { LoadingState() }
        }
        loadState.append is LoadState.Loading -> {
          item { LoadingState() }
        }
        loadState.refresh is LoadState.Error -> {
          item { ErrorState { refresh() } }
        }
        loadState.append is LoadState.Error -> {
          item { ErrorState { refresh() } }
        }
      }
    }
  }
}
