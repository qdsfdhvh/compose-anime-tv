package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.focusClick
import com.seiko.compose.focuskit.scrollToIndex
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.data.model.anime.Anime
import com.seiko.tv.anime.ui.composer.navigation.Router
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor
import com.seiko.tv.anime.ui.theme.uiValue

@Composable
fun TvTitleGroup(
  title: String,
  list: List<Anime>,
  modifier: Modifier = Modifier,
) {
  val navController = LocalAppNavigator.current

  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable(stateSaver = autoSaver()) { mutableStateOf(0) }
  var isParentFocused by remember { mutableStateOf(false) }

  Column {
    Text(
      text = title,
      style = MaterialTheme.typography.h6,
      modifier = Modifier.padding(start = 15.dp, top = 10.dp),
    )
    LazyRow(
      modifier = modifier
        .onFocusChanged { isParentFocused = it.hasFocus || it.isFocused }
        .focusTarget(),
      state = listState,
    ) {
      itemsIndexed(list) { index, item ->
        val focusRequester = remember { FocusRequester() }
        var isFocused by remember { mutableStateOf(false) }
        GroupItem(
          modifier = Modifier
            .onFocusChanged {
              isFocused = it.isFocused
              if (isFocused) focusIndex = index
            }
            .focusClick {
              focusRequester.requestFocus()
              navController.push(Router.Detail(item.uri))
            }
            .focusOrder(focusRequester)
            .focusTarget(),
          item = item,
          isFocused = isFocused,
        )

        if (isParentFocused && focusIndex == index) {
          SideEffect { focusRequester.requestFocus() }
        }
      }
    }

    if (isParentFocused) {
      LaunchedEffect(focusIndex) {
        listState.scrollToIndex(focusIndex, ScrollBehaviour.Horizontal)
      }
    }
  }
}

@Composable
fun GroupItem(
  item: Anime,
  isFocused: Boolean,
  modifier: Modifier = Modifier,
) {
  val scale by animateFloatAsState(if (isFocused) 1.1f else 1f)
  Box(
    modifier = modifier
      .scale(scale)
      .padding(
        horizontal = MaterialTheme.uiValue.paddingHorizontal,
        vertical = MaterialTheme.uiValue.paddingVertical
      )
      .shadow(if (isFocused) MaterialTheme.uiValue.elevation else 0.dp)
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
            uri = "/show/273.html",
          ),
          isFocused = true
        )
        GroupItem(
          Anime(
            title = "妖精的尾巴",
            cover = "http://css.njhzmxx.com/comic/focus/2018/10/201810070913.jpg",
            uri = "/show/273.html",
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
            uri = "/show/273.html",
          ),
          Anime(
            title = "妖精的尾巴",
            cover = "http://css.njhzmxx.com/comic/focus/2018/10/201810070913.jpg",
            uri = "/show/273.html",
          )
        )
      )
    }
  }
}
