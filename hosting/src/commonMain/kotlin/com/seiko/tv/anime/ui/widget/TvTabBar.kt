package com.seiko.tv.anime.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.tv.anime.model.anime.AnimeTab

@Composable
fun TvTabBar(
  tabList: List<AnimeTab>,
  focusIndex: Int,
  modifier: Modifier = Modifier,
  onFocusIndexChange: (Int) -> Unit = {}
) {
  val listState = rememberLazyListState()

  var parentIsFocused by remember { mutableStateOf(false) }
  var parentHasFocused by remember { mutableStateOf(false) }

  LazyRow(
    modifier = modifier
      .onFocusChanged {
        parentHasFocused = it.hasFocus
        parentIsFocused = it.isFocused
      }
      .focusTarget(),
    state = listState
  ) {
    itemsIndexed(tabList) { index, tab ->
      val focusRequester = remember { FocusRequester() }
      var isFocused by remember { mutableStateOf(false) }
      val scale by animateFloatAsState(if (isFocused) 1.1f else 1f)
      TvTabBarItem(
        modifier = Modifier
          .onFocusChanged {
            isFocused = it.isFocused
            if (isFocused) onFocusIndexChange(index)
          }
          .clickable { focusRequester.requestFocus() }
          .focusRequester(focusRequester)
          .focusTarget()
          .scale(scale),
        title = tab.title,
        isFocused = isFocused,
        isSelected = focusIndex == index,
      )
      if (parentIsFocused && focusIndex == index) {
        SideEffect {
          focusRequester.requestFocus()
        }
      }
    }
  }

  if (parentHasFocused) {
    LaunchedEffect(focusIndex) {
      listState.animateScrollToItem(focusIndex, ScrollBehaviour.Horizontal)
    }
  }
}

@Composable
internal fun TvTabBarItem(
  title: String,
  isFocused: Boolean,
  isSelected: Boolean,
  modifier: Modifier = Modifier
) {
  Surface(
    color = if (isFocused || isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent,
    contentColor = MaterialTheme.colorScheme.onSurface,
    shape = CircleShape,
    modifier = modifier.padding(8.dp),
  ) {
    Text(title, Modifier.padding(16.dp, 8.dp))
  }
}
