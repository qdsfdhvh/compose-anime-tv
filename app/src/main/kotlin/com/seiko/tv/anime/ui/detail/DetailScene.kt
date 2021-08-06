package com.seiko.tv.anime.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.TvLazyColumn
import com.seiko.compose.focuskit.rememberContainerTvFocusItem
import com.seiko.tv.anime.component.foundation.NetworkImage
import com.seiko.tv.anime.di.assisted.assistedViewModel
import com.seiko.tv.anime.ui.theme.AnimeTvTheme

@Composable
fun DetailScene(id: Int) {
  val viewModel = assistedViewModel<DetailViewModel.AssistedFactory, DetailViewModel> { factory ->
    factory.create(id)
  }

  val container = rememberContainerTvFocusItem()

  val info by viewModel.info.collectAsState()

  Surface(color = MaterialTheme.colors.background) {
    TvLazyColumn(
      container = container,
      modifier = Modifier.fillMaxSize(),
    ) {
      item {
        TvAnimeInfo(
          title = info.title,
          cover = info.cover,
          releaseTime = info.releaseTime,
          state = info.state,
          tags = info.tags,
          description = info.description,
        )
      }

      item {
        TvEpisodeList()
      }
    }
  }
}

@Composable
private fun TvAnimeInfo(
  modifier: Modifier = Modifier,
  title: String = "",
  cover: String = "",
  releaseTime: String = "",
  state: String = "",
  tags: List<String> = emptyList(),
  description: String = "",
) {
  Row {
    Column(
      modifier = modifier
        .weight(0.7f),
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.h3,
      )
      Spacer(modifier = Modifier.height(20.dp))
      Row(
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = releaseTime,
          style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
          text = state,
          style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.width(20.dp))
        for (tag in tags) {
          Text(
            modifier = Modifier
              .widthIn(min = 60.dp)
              .border(1.dp, MaterialTheme.colors.onSurface, RoundedCornerShape(2.dp))
              .padding(2.dp),
            text = tag,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
          )
          Spacer(modifier = Modifier.width(10.dp))
        }
      }
      Spacer(modifier = Modifier.height(20.dp))
      Text(
        text = description,
        style = MaterialTheme.typography.subtitle1
      )
    }

    NetworkImage(
      modifier = Modifier
        .weight(0.3f)
        .size(200.dp, 300.dp)
        .background(Color.Blue),
      data = cover,
    )
  }
}

@Composable
private fun TvEpisodeList() {

}

@Preview(widthDp = 1280, heightDp = 720)
@Composable
private fun TvAnimeInfoPreview() {
  AnimeTvTheme {
    Surface(color = MaterialTheme.colors.background) {
      TvAnimeInfo(
        title = "小林家的龙女仆 第二季",
        releaseTime = "2021-07",
        state = "更新至第5集",
        tags = listOf("搞笑", "奇幻", "百合", "治愈"),
        description = "《小林家的龙女仆 第二季》主人公小林是一位女性系统工程师，某天她喝酒喝的很醉兴冲冲的跑到了山上遇到了一头龙，酒醉之下的小林对着龙大倒苦水。听到龙说自己无家可归时，小林趁着酒意开玩笑的说“那你就来我家吧”，之后龙真的就跑到小林的家里，并且还变成了一位女仆——于是一位龙女仆和小林的同居生活就这样开始了……",
      )
    }
  }
}