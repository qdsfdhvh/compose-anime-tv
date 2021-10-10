package com.seiko.tv.anime.ui.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.ui.common.SpacerWidth
import com.seiko.tv.anime.ui.common.foundation.FocusableButton
import com.seiko.tv.anime.ui.common.foundation.NetworkImage
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.uiValue

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DetailAnimeInfo(
  modifier: Modifier = Modifier,
  title: String = "",
  cover: String = "",
  releaseTime: String = "",
  state: String = "",
  tags: List<String> = emptyList(),
  description: String = "",
  isFavorite: Boolean = false,
  onFavoriteClick: () -> Unit = {}
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isFocused by interactionSource.collectIsFocusedAsState()

  val btnStarFocusRequester = remember { FocusRequester() }

  Row(
    modifier = modifier
      .focusable(interactionSource = interactionSource)
      .padding(MaterialTheme.uiValue.paddingHorizontal),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Column(
      modifier = Modifier
        .weight(0.7f)
        .wrapContentWidth(),
      verticalArrangement = Arrangement.spacedBy(MaterialTheme.uiValue.paddingHorizontal)
    ) {
      DetailAnimeInfoDesc(
        title = title,
        releaseTime = releaseTime,
        state = state,
        tags = tags,
        description = description
      )

      FocusableButton(
        modifier = Modifier.focusRequester(btnStarFocusRequester),
        onClick = onFavoriteClick
      ) {
        Icon(
          painter = rememberVectorPainter(
            image = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
          ),
          contentDescription = null
        )
      }
    }

    Box(
      modifier = Modifier
        .weight(0.3f)
        .fillMaxHeight()
    ) {
      NetworkImage(
        data = cover,
        modifier = Modifier
          .size(200.dp, 300.dp)
          .align(Alignment.Center)
      )
    }
  }

  LaunchedEffect(title, isFocused) {
    if (isFocused) {
      btnStarFocusRequester.requestFocus()
    }
  }
}

@Composable
private fun DetailAnimeInfoDesc(
  title: String,
  releaseTime: String,
  state: String,
  tags: List<String>,
  description: String,
) {
  Text(title, style = MaterialTheme.typography.h3)
  Row(Modifier.fillMaxWidth()) {
    Text(releaseTime, style = MaterialTheme.typography.body1)
    SpacerWidth(20.dp)
    Text(state, style = MaterialTheme.typography.body1)
    SpacerWidth(20.dp)
    tags.forEach { tag ->
      Text(
        text = tag,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center,
        modifier = Modifier
          .widthIn(min = 60.dp)
          .border(1.dp, MaterialTheme.colors.onSurface, MaterialTheme.shapes.small)
          .padding(2.dp),
      )
      SpacerWidth(10.dp)
    }
  }
  Text(
    description,
    style = MaterialTheme.typography.subtitle1,
    overflow = TextOverflow.Ellipsis,
    maxLines = 5
  )
}

@Preview(widthDp = 1280, heightDp = 400)
@Composable
private fun DetailAnimeInfoPreview() {
  AnimeTvTheme {
    Surface(color = MaterialTheme.colors.background) {
      DetailAnimeInfo(
        title = "魔法纪录 魔法少女小圆外传 第二季 -觉醒前夜-",
        releaseTime = "日本",
        state = "更新至第6集",
        tags = listOf("搞笑", "奇幻", "百合", "治愈"),
        description = "《魔法纪录魔法少女小圆外传第二季-觉醒前夜-》TV动画《魔法少女小圆☆魔法少女小圆外传2ndSEASON-觉醒前夜-》的放送决定于7月31日（周六）24:00开始！在第二季SEASON放送之前，7月3日（周六）开始将连续3周播放第一季SEASON的总集篇，7月24日（周六）将播出第二季SEASON之前的特别节目！此外，FinalSeason-浅梦之晓预计将于2021年末播出！作为实现愿望的代价，在无人知晓的情况下不断战斗的魔法少女们。然而，环伊吕波，却忘记了自己的愿望。“成为魔法少女的时候，我是许了什么愿望来着？”日常当中突然出现的空洞。失去了的某件重要之物。连理由都不知道，就这样每天不停地战斗……就在此时，魔法少女之间开始流传某个传闻。“若能去神滨的话，魔法少女就能得到拯救”。魔法少女与传闻集中的城市，神滨市。寻求着自己失去的愿望，环伊吕波的故事开始了——",
      )
    }
  }
}

@Preview(widthDp = 1280, heightDp = 400)
@Composable
private fun DetailAnimeInfoV2Preview() {
  AnimeTvTheme {
    Surface(color = MaterialTheme.colors.background) {
      DetailAnimeInfo(
        title = "境界触发者 第三季",
        releaseTime = "日本",
        state = "更新至第1集",
        tags = listOf("日语", "tv", "百合", "治愈"),
        description = "《境界触发者第三季》TV动画《境界触发者》第3季制作决定！",
      )
    }
  }
}
