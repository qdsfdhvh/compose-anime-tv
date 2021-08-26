package com.seiko.tv.anime.component.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.ContainerTvFocusItem
import com.seiko.compose.focuskit.TvControllerKey
import com.seiko.compose.focuskit.TvLazyRow
import com.seiko.compose.focuskit.handleTvKey
import com.seiko.compose.focuskit.rememberContainerTvFocusItem
import com.seiko.compose.focuskit.rememberTvFocusItem
import com.seiko.compose.focuskit.tvFocusTarget
import com.seiko.tv.anime.LocalAppNavigator
import com.seiko.tv.anime.model.AnimeEpisode
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor

@Composable
fun TvEpisodeList(
  title: String,
  list: List<AnimeEpisode>,
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
          key = title + index,
          container = container,
          index = index
        )

        var isFocused by remember { mutableStateOf(false) }
        EpisodeItem(
          modifier = Modifier
            .clickable {
              navigator.push(item.actionUrl)
            }
            .handleTvKey(TvControllerKey.Enter) {
              navigator.push(item.actionUrl)
            }
            .onFocusChanged {
              isFocused = it.isFocused
            }
            .tvFocusTarget(focusItem),
          episode = item,
          isFocused = isFocused,
        )
      }
    }
  }
}

@Composable
private fun EpisodeItem(
  episode: AnimeEpisode,
  isFocused: Boolean,
  modifier: Modifier = Modifier,
) {
  val scale by animateFloatAsState(if (isFocused) 1.2f else 1f)
  Text(
    text = episode.title,
    color = MaterialTheme.colors.onSurface,
    modifier = modifier
      .scale(scale)
      .padding(10.dp)
      .shadow(if (isFocused) 5.dp else 0.dp, RoundedCornerShape(4.dp))
      .background(MaterialTheme.colors.surface, RoundedCornerShape(4.dp))
      .padding(horizontal = 20.dp, vertical = 10.dp),
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
