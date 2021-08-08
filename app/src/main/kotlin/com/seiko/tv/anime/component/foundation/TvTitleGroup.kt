package com.seiko.tv.anime.component.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.*
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.model.Anime
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor
import com.seiko.tv.anime.util.extensions.clickableNoRipple

@Composable
fun TvTitleGroup(
  title: String,
  list: List<Anime>,
  parent: ContainerTvFocusItem? = null
) {
  val container = parent ?: rememberContainerTvFocusItem()
  val navigator = LocalAppNavigator.current

  Column {
    Text(
      text = title,
      style = MaterialTheme.typography.h6,
      modifier = Modifier
        .padding(start = 15.dp, top = 10.dp),
    )
    TvLazyRow(container) {
      itemsIndexed(list) { index, item ->
        val focusItem = rememberTvFocusItem(
          key = item,
          container = container,
          index = index
        )
        var isFocused by remember { mutableStateOf(false) }
        GroupItem(
          modifier = Modifier
            .clickableNoRipple {
              navigator.push(item.actionUrl)
            }
            .handleTvKey(TvControllerKey.Enter) {
              navigator.push(item.actionUrl)
              true
            }
            .onFocusChanged {
              isFocused = it.isFocused
            }
            .tvFocusTarget(focusItem),
          item = item,
          isFocused = isFocused,
        )
      }
    }
  }
}

@Composable
private fun GroupItem(
  item: Anime,
  isFocused: Boolean,
  modifier: Modifier = Modifier,
) {
  val scale by animateFloatAsState(if (isFocused) 1.1f else 1f)
  Box(
    modifier = modifier
      .scale(scale)
      .padding(horizontal = 15.dp, vertical = 10.dp)
      .shadow(if (isFocused) 5.dp else 0.dp)
      .background(MaterialTheme.colors.surface)
      .border(1.dp, if (isFocused) MaterialTheme.colors.surface else Color.Transparent)
      .size(140.dp, 200.dp)
  ) {
    NetworkImage(
      data = item.cover,
      modifier = Modifier
        .fillMaxSize()
    )
    Text(
      text = item.title,
      color = Color.White,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.caption,
      modifier = Modifier
        .background(Color.Gray.copy(alpha = 0.5f))
        .padding(5.dp)
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun GroupItemPreview() {
  AnimeTvTheme {
    Surface(color = backgroundColor) {
      Row(Modifier.padding(5.dp)) {
        GroupItem(
          Anime(
            title = "妖精的尾巴",
            cover = "http://css.njhzmxx.com/comic/focus/2018/10/201810070913.jpg",
            actionUrl = "/show/273.html",
          ),
          isFocused = true
        )
        GroupItem(
          Anime(
            title = "妖精的尾巴",
            cover = "http://css.njhzmxx.com/comic/focus/2018/10/201810070913.jpg",
            actionUrl = "/show/273.html",
          ),
          isFocused = false
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun TvTitleGroupPreview() {
  AnimeTvTheme {
    Surface(color = backgroundColor) {
      TvTitleGroup(
        title = "最新更新",
        list = listOf(
          Anime(
            title = "妖精的尾巴",
            cover = "http://css.njhzmxx.com/comic/focus/2018/10/201810070913.jpg",
            actionUrl = "/show/273.html",
          ),
          Anime(
            title = "妖精的尾巴",
            cover = "http://css.njhzmxx.com/comic/focus/2018/10/201810070913.jpg",
            actionUrl = "/show/273.html",
          )
        )
      )
    }
  }
}