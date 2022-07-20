package com.seiko.tv.anime.ui.foundation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun LoadingIndicator(
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    CircularProgressIndicator()
  }
}

@Composable
fun ErrorState(
  onRetry: () -> Unit,
  message: String? = null,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    OutlinedButton(onClick = onRetry) {
      Text(
        text = "Retry",
        style = TextStyle(color = MaterialTheme.colorScheme.onPrimary, fontSize = 20.sp)
      )
    }
    if (!message.isNullOrEmpty()) {
      Spacer(Modifier.height(8.dp))
      Text(message)
    }
  }
}

@Suppress("FunctionName")
fun <T : Any> LazyListScope.ScreenState(list: LazyPagingItems<T>) {
  list.apply {
    when {
      loadState.refresh is LoadState.Loading -> {
        item { LoadingIndicator() }
      }

      loadState.append is LoadState.Loading -> {
        item { LoadingIndicator() }
      }

      loadState.refresh is LoadState.Error -> {
        item { ErrorState(onRetry = { refresh() }) }
      }

      loadState.append is LoadState.Error -> {
        item { ErrorState(onRetry = { refresh() }) }
      }
    }
  }
}
