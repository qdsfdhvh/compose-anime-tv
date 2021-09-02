package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.seiko.compose.focuskit.TvLazyRow
import com.seiko.compose.focuskit.collectFocusIndexAsState
import com.seiko.compose.focuskit.rememberFocusRequesters
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor

@Composable
fun TvTabBar(
  tabList: List<String>,
  modifier: Modifier = Modifier,
) {
  val focusRequesters = rememberFocusRequesters(tabList)
  val interactionSource = remember { MutableInteractionSource() }
  val focusIndex by interactionSource.collectFocusIndexAsState()
  var isParentFocused by remember { mutableStateOf(false) }

  TvLazyRow(
    modifier = modifier.onFocusChanged { isParentFocused = it.hasFocus || it.isFocused },
    interactionSource = interactionSource,
  ) {
    itemsIndexed(tabList) { index, title ->
      val itemInteractionSource = remember { MutableInteractionSource() }
      TvTabBarItem(
        modifier = Modifier
          .focusRequester(focusRequesters[index])
          .focusable(interactionSource = itemInteractionSource),
        title = title,
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
