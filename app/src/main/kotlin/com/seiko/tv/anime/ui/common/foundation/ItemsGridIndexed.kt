package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems

fun <T : Any> LazyListScope.itemsGridIndexed(
  data: LazyPagingItems<T>,
  rowSize: Int,
  itemContent: @Composable BoxScope.(Int, T) -> Unit
) {
  val rows = (data.itemCount + rowSize - 1) / rowSize
  items(rows) { rowIndex ->
    Row(Modifier.fillMaxWidth()) {
      for (columnIndex in 0 until rowSize) {
        val itemIndex = rowIndex * rowSize + columnIndex
        if (itemIndex < data.itemCount) {
          Box(
            modifier = Modifier.weight(1f, fill = true),
            contentAlignment = Alignment.Center
          ) {
            data[itemIndex]?.let { item ->
              itemContent(itemIndex, item)
            }
          }
        } else {
          Spacer(Modifier.weight(1f, fill = true))
        }
      }
    }
  }
}
