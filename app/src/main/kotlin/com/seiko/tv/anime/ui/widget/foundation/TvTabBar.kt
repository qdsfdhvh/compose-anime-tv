package com.seiko.tv.anime.ui.widget.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor
import com.seiko.tv.anime.util.extensions.focusTarget

@Composable
fun TvTabBar(tabData: List<String>) {
  var tabIndex by remember { mutableStateOf(0) }
  LazyRow {
    itemsIndexed(tabData) { index, title ->
      val isSelected = tabIndex == index
      var isFocused by remember { mutableStateOf(isSelected) }
      TvTabBarItem(
        title = title,
        isFocused = isFocused,
        isSelected = isSelected,
        onFocusChanged = {
          isFocused = it.isFocused
          if (isFocused) {
            tabIndex = index
          }
        }
      )
    }
  }
}

@Composable
private fun TvTabBarItem(
  title: String,
  isFocused: Boolean,
  isSelected: Boolean,
  onFocusChanged: (FocusState) -> Unit = {}
) {
  val focusRequester = remember { FocusRequester() }
  val scale by animateFloatAsState(if (isFocused) 1.1f else 1f)
  val background = if (isSelected) MaterialTheme.colors.surface else Color.Transparent
  Text(
    text = title,
    color = when {
      isFocused -> Color.Black
      else -> Color.Unspecified
    },
    style = MaterialTheme.typography.body1,
    modifier = Modifier
      .focusTarget(focusRequester, onFocusChanged)
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