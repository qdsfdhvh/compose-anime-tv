package com.seiko.tv.anime.ui.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.seiko.tv.anime.model.anime.AnimeTag
import com.seiko.tv.anime.ui.theme.AnimeTvTheme

@Preview(widthDp = 1280, heightDp = 500)
@Composable
fun TagItemPreview() {
  AnimeTvTheme {
    Column(Modifier.background(MaterialTheme.colorScheme.background)) {
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