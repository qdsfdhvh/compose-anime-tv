package com.seiko.tv.anime.ui.widget.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.util.extensions.focusTarget

@Composable
fun TvTab(tabData: List<String>) {
  var tabIndex by remember { mutableStateOf(1) }

  LazyRow {
    itemsIndexed(tabData) { index, text ->
      val focusRequester = remember { FocusRequester() }
      var isFocused by remember { mutableStateOf(tabIndex == index) }
      val isSelected = tabIndex == index

      Text(
        text = text,
        color = when {
          isFocused -> Color.White
          isSelected -> MaterialTheme.colors.primary
          else -> Color.Unspecified
        },
        style = MaterialTheme.typography.body1,
        modifier = Modifier
          .focusTarget(focusRequester) {
            isFocused = it.isFocused
            if (isFocused) {
              tabIndex = index
            }
          }
          .padding(5.dp)
          .background(
            if (isFocused) MaterialTheme.colors.primary else Color.Transparent,
            CircleShape
          )
          .padding(15.dp, 5.dp)
      )
    }
  }
}

@Preview
@Composable
fun TvTabPreview() {
  TvTab(
    listOf(
      "首页",
      "日本动漫",
      "国产动漫",
      "美国动漫",
      "动漫电影",
      "亲子动漫",
      "新番动漫",
      "剧场版",
      "OVA版",
      "真人版",
      "动漫专题",
      "最近更新",
    )
  )
}