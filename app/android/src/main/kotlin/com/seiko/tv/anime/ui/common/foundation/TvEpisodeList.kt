package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.ScrollBehaviour
import com.seiko.compose.focuskit.animateScrollToItem
import com.seiko.tv.anime.data.model.anime.AnimeEpisode
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor
import com.seiko.tv.anime.ui.theme.uiValue

@Composable
fun TvEpisodeList(
  title: String,
  list: List<AnimeEpisode>,
  onEpisodeClick: (AnimeEpisode) -> Unit,
  modifier: Modifier = Modifier
) {
  val listState = rememberLazyListState()
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  var parentIsFocused by remember { mutableStateOf(false) }
  var parentHasFocused by remember { mutableStateOf(false) }

  Column {
    Text(
      text = title,
      style = MaterialTheme.typography.titleSmall,
      modifier = Modifier.padding(
        start = MaterialTheme.uiValue.paddingHorizontal,
        top = MaterialTheme.uiValue.paddingVertical
      )
    )

    LazyRow(
      modifier = modifier
        .onFocusChanged {
          parentHasFocused = it.hasFocus
          parentIsFocused = it.isFocused
        }
        .focusTarget(),
      state = listState
    ) {
      itemsIndexed(list) { index, item ->
        val focusRequester = remember { FocusRequester() }
        var isFocused by remember { mutableStateOf(false) }

        EpisodeItem(
          modifier = Modifier
            .onFocusChanged {
              isFocused = it.isFocused
              if (isFocused) focusIndex = index
            }
            .clickable {
              focusRequester.requestFocus()
              onEpisodeClick.invoke(item)
            }
            .focusRequester(focusRequester)
            .focusTarget(),
          episode = item,
          isFocused = isFocused
        )

        if (parentIsFocused && focusIndex == index) {
          SideEffect {
            focusRequester.requestFocus()
          }
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
private fun EpisodeItem(
  episode: AnimeEpisode,
  isFocused: Boolean,
  modifier: Modifier = Modifier
) {
  val scale by animateFloatAsState(if (isFocused) 1.2f else 1f)
  Text(
    text = episode.title,
    color = MaterialTheme.colorScheme.onSurface,
    modifier = modifier
      .scale(scale)
      .padding(MaterialTheme.uiValue.paddingHorizontalSmall)
      .shadow(
        if (isFocused) MaterialTheme.uiValue.elevation else 0.dp,
        MaterialTheme.shapes.medium
      )
      .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
      .padding(
        horizontal = MaterialTheme.uiValue.paddingHorizontal,
        vertical = MaterialTheme.uiValue.paddingVertical
      )
  )
}

@Preview
@Composable
fun EpisodeItemPreview() {
  AnimeTvTheme {
    Surface(color = backgroundColor) {
      Row(modifier = Modifier.padding(5.dp)) {
        EpisodeItem(
          episode = AnimeEpisode("第01集", ""),
          isFocused = true
        )
        EpisodeItem(
          episode = AnimeEpisode("第02集", ""),
          isFocused = false
        )
      }
    }
  }
}
