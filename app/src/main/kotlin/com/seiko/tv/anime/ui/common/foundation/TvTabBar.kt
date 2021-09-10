package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.collectFocusIndexAsState
import com.seiko.compose.focuskit.focusScrollHorizontal
import com.seiko.compose.focuskit.rememberFocusRequesters
import com.seiko.tv.anime.data.model.anime.AnimeTab
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor
import com.seiko.tv.anime.ui.theme.uiValue

@Composable
fun TvTabBar(
  tabList: List<AnimeTab>,
  modifier: Modifier = Modifier,
  listState: LazyListState = rememberLazyListState(),
) {
  val focusRequesters = rememberFocusRequesters(tabList)
  val focusIndex by listState.interactionSource.collectFocusIndexAsState()
  var isParentFocused by remember { mutableStateOf(false) }

  LazyRow(
    modifier = modifier
      .onFocusChanged { isParentFocused = it.hasFocus || it.isFocused }
      .focusScrollHorizontal(listState)
      .focusable(),
    state = listState,
  ) {
    itemsIndexed(tabList) { index, tab ->
      val itemInteractionSource = remember { MutableInteractionSource() }
      TvTabBarItem(
        modifier = Modifier
          .focusRequester(focusRequesters[index])
          .focusable(interactionSource = itemInteractionSource),
        title = tab.title,
        isFocused = itemInteractionSource.collectIsFocusedAsState().value,
        isSelected = focusIndex == index,
      )
    }
  }

  LaunchedEffect(focusIndex, isParentFocused) {
    if (isParentFocused) {
      focusRequesters.getOrNull(focusIndex)?.requestFocus()
    }
  }
}

@Composable
private fun TvTabBarItem(
  title: String,
  isFocused: Boolean,
  isSelected: Boolean,
  modifier: Modifier = Modifier,
) {
  val scale by animateFloatAsState(if (isFocused) 1.1f else 1f)
  val background = if (isSelected) MaterialTheme.colors.surface else Color.Transparent
  Text(
    text = title,
    color = when {
      isFocused -> Color.Black
      else -> Color.Unspecified
    },
    style = MaterialTheme.typography.body1,
    modifier = modifier
      .scale(scale)
      .padding(5.dp)
      .shadow(if (isFocused) MaterialTheme.uiValue.elevation else 0.dp, CircleShape)
      .background(background, CircleShape)
      .padding(15.dp, 5.dp)
  )
}

@Preview(showBackground = true)
@Composable
fun TvTabItemPreview() {
  AnimeTvTheme {
    Surface(color = backgroundColor) {
      Row(
        modifier = Modifier.padding(5.dp)
      ) {
        TvTabBarItem("首页", isFocused = true, isSelected = true)
        TvTabBarItem("日本动漫", isFocused = false, isSelected = true)
        TvTabBarItem("国产动漫", isFocused = false, isSelected = false)
      }
    }
  }
}
