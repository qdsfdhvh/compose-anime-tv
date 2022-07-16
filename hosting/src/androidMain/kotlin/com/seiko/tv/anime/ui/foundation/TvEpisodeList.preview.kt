package com.seiko.tv.anime.ui.foundation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.model.anime.AnimeEpisode
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor

@Preview
@Composable
fun TvEpisodeListPreview() {
  AnimeTvTheme {
    TvEpisodeList(
      title = "Title",
      list = listOf(
        AnimeEpisode("第01集", ""),
        AnimeEpisode("第02集", ""),
      ),
      onEpisodeClick = {},
    )
  }
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
