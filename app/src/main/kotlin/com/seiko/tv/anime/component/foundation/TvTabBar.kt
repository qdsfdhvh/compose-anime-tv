package com.seiko.tv.anime.component.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.*
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor

@Composable
fun TvTabBar(
  tabList: List<String>,
  parent: ContainerTvFocusItem? = null
) {
  val container = parent ?: rememberContainerTvFocusItem()
  var tabIndex by remember { mutableStateOf(0) }
  TvLazyRow(container) {
    itemsIndexed(tabList) { index, title ->
      val focusItem = rememberTvFocusItem(
        key = title + index,
        container = container,
        index = index
      )

      val isSelected = tabIndex == index
      var isFocused by remember { mutableStateOf(false) }
      TvTabBarItem(
        modifier = Modifier
          .onTvFocusChanged(focusItem) {
            isFocused = it.isFocused
            if (isFocused) {
              tabIndex = index
            }
          },
        title = title,
        isFocused = isFocused,
        isSelected = isSelected,
      )
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
      .shadow(if (isFocused) 5.dp else 0.dp, CircleShape)
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