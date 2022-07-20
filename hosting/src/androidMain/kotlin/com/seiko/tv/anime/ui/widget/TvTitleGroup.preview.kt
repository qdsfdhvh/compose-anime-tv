package com.seiko.tv.anime.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.model.anime.Anime
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor

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
            uri = "/show/273.html"
          ),
          Anime(
            title = "妖精的尾巴",
            cover = "http://css.njhzmxx.com/comic/focus/2018/10/201810070913.jpg",
            uri = "/show/273.html"
          )
        ),
        onAnimeClick = {},
      )
    }
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
            uri = "/show/273.html"
          ),
          isFocused = true
        )
        GroupItem(
          Anime(
            title = "妖精的尾巴",
            cover = "http://css.njhzmxx.com/comic/focus/2018/10/201810070913.jpg",
            uri = "/show/273.html"
          ),
          isFocused = false
        )
      }
    }
  }
}
