package com.seiko.tv.anime.ui.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.compose.focuskit.focusClick
import com.seiko.compose.focuskit.rememberFocusRequesterManager
import com.seiko.tv.anime.data.model.anime.AnimeTag
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.common.SpacerHeight
import com.seiko.tv.anime.ui.common.SpacerWidth
import com.seiko.tv.anime.ui.common.foundation.NetworkImage
import com.seiko.tv.anime.ui.common.foundation.screenState
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.uiValue
import moe.tlaster.precompose.navigation.NavController

@Composable
fun TagScene(
  navController: NavController,
  uri: String,
) {
  val viewModel = tagViewModel(uri)
  val animes = viewModel.animes.collectAsLazyPagingItems()

  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  Surface(
    color = MaterialTheme.colors.background,
    modifier = Modifier.fillMaxSize(),
  ) {
    LazyColumn(state = listState) {
      itemsIndexed(animes) { index, anime ->
        anime ?: return@itemsIndexed

        val focusRequester = remember { FocusRequester() }
        var isFocused by remember { mutableStateOf(false) }

        TagItem(
          title = anime.title,
          cover = anime.cover,
          update = anime.update,
          tags = anime.tags,
          description = anime.description,
          isFocused = isFocused,
          modifier = Modifier
            .onFocusChanged {
              isFocused = it.isFocused
              if (isFocused) focusIndex = index
            }
            .focusClick {
              focusRequester.requestFocus()
              navigator.push(Router.Detail(anime.uri))
            }
            .focusOrder(focusRequester)
        )

        if (focusIndex == index) {
          SideEffect {
            focusRequester.requestFocus()
          }
        }
      }

      screenState(animes)
    }
  }

  LaunchedEffect(focusIndex) {
    listState.animateScrollToItem(focusIndex, ScrollBehaviour.Vertical)
  }
}

@Composable
fun TagItem(
  title: String,
  cover: String,
  update: String,
  tags: List<AnimeTag>,
  description: String,
  isFocused: Boolean,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .padding(10.dp)
      .shadow(if (isFocused) MaterialTheme.uiValue.elevation else 0.dp, MaterialTheme.shapes.small)
      .background(
        if (isFocused) MaterialTheme.colors.surface else Color.Transparent,
        MaterialTheme.shapes.medium
      )
      .padding(10.dp)
      .fillMaxWidth()
      .focusTarget(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    NetworkImage(cover, Modifier.size(75.dp, 100.dp))
    SpacerWidth(width = 10.dp)
    Column {
      Text(
        title,
        style = MaterialTheme.typography.subtitle1,
      )
      SpacerHeight(height = 5.dp)
      Text(
        update,
        color = Color.Red,
        style = MaterialTheme.typography.caption,
      )
      SpacerHeight(height = 5.dp)
      Text(
        "类型：${tags.joinToString { it.title }}",
        color = Color.Gray,
        style = MaterialTheme.typography.caption,
      )
      SpacerHeight(height = 10.dp)
      Text(
        description,
        color = Color.DarkGray,
        style = MaterialTheme.typography.body2,
        maxLines = 2
      )
    }
  }
}

@Preview(widthDp = 1280, heightDp = 500)
@Composable
fun TagItemPreview() {
  AnimeTvTheme {
    Column(Modifier.background(MaterialTheme.colors.background)) {
      TagItem(
        title = "Muv-Luv Alternative",
        cover = "http://css.njhzmxx.com/acg/2021/05/27/20210527051205790.jpg",
        update = "更新至1集",
        tags = listOf("科幻", "机战").map { AnimeTag(it, "") },
        description = "电视动画《Muv-Luv Alternative》改编自Âge开发的同名游戏作品，2019年10月22日宣布了动画化的决定。2021年10月播出" +
          "那是在极限的世界战斗的人们之间牵绊的物语&mdash;&mdash;存在于这一时空的，无数平行世界之一&mdash;&mdash;在那...",
        isFocused = true
      )

      TagItem(
        title = "Muv-Luv Alternative",
        cover = "http://css.njhzmxx.com/acg/2021/05/27/20210527051205790.jpg",
        update = "更新至1集",
        tags = listOf("科幻", "机战").map { AnimeTag(it, "") },
        description = "电视动画《Muv-Luv Alternative》改编自Âge开发的同名游戏作品，2019年10月22日宣布了动画化的决定。2021年10月播出" +
          "那是在极限的世界战斗的人们之间牵绊的物语&mdash;&mdash;存在于这一时空的，无数平行世界之一&mdash;&mdash;在那...",
        isFocused = false
      )
    }
  }
}
