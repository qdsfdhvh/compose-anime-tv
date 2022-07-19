package com.seiko.tv.anime.ui.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.tv.anime.model.anime.AnimeTag
import com.seiko.tv.anime.model.anime.AnimeTagPageItem
import com.seiko.tv.anime.ui.Router
import com.seiko.tv.anime.ui.foundation.NetworkImage
import com.seiko.tv.anime.ui.foundation.ScreenState
import com.seiko.tv.anime.ui.theme.uiValue
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.rememberPresenter

@Composable
fun TagScene(
  navigator: Navigator,
  uri: String
) {
  val stateFlow = rememberPresenter { TagPresenter(uri) }
  when (val state = stateFlow.collectAsState().value) {
    is TagState.Success -> {
      TagScene(
        pagingItems = state.pagingItems,
        onItemClick = { tag ->
          navigator.navigate(Router.Detail(tag.uri))
        }
      )
    }
  }
}

@Composable
fun TagScene(
  pagingItems: LazyPagingItems<AnimeTagPageItem>,
  onItemClick: (AnimeTagPageItem) -> Unit,
) {
  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  Surface(
    color = MaterialTheme.colorScheme.background,
    modifier = Modifier.fillMaxSize()
  ) {
    LazyColumn(state = listState) {
      itemsIndexed(pagingItems) { index, tag ->
        tag ?: return@itemsIndexed

        val focusRequester = remember { FocusRequester() }
        var isFocused by remember { mutableStateOf(false) }

        TagItem(
          title = tag.title,
          cover = tag.cover,
          update = tag.update,
          tags = tag.tags,
          description = tag.description,
          isFocused = isFocused,
          modifier = Modifier
            .onFocusChanged {
              isFocused = it.isFocused
              if (isFocused) focusIndex = index
            }
            .clickable {
              focusRequester.requestFocus()
              onItemClick.invoke(tag)
            }
            .focusRequester(focusRequester)
        )

        if (focusIndex == index) {
          SideEffect {
            focusRequester.requestFocus()
          }
        }
      }

      ScreenState(pagingItems)
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
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .padding(10.dp)
      .shadow(if (isFocused) MaterialTheme.uiValue.elevation else 0.dp, MaterialTheme.shapes.small)
      .background(
        if (isFocused) MaterialTheme.colorScheme.surface else Color.Transparent,
        MaterialTheme.shapes.medium
      )
      .padding(10.dp)
      .fillMaxWidth()
      .focusTarget(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    NetworkImage(cover, Modifier.size(75.dp, 100.dp))
    Spacer(Modifier.width(10.dp))
    Column {
      Text(
        title,
        style = MaterialTheme.typography.titleMedium
      )
      Spacer(Modifier.height(5.dp))
      Text(
        update,
        color = Color.Red,
        style = MaterialTheme.typography.labelMedium
      )
      Spacer(Modifier.height(5.dp))
      Text(
        "类型：${tags.joinToString { it.title }}",
        color = Color.Gray,
        style = MaterialTheme.typography.labelMedium
      )
      Spacer(Modifier.height(10.dp))
      Text(
        description,
        color = Color.DarkGray,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 2
      )
    }
  }
}