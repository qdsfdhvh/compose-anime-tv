package com.seiko.tv.anime.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.onFocusDirection
import com.seiko.tv.anime.data.model.anime.AnimeTag
import com.seiko.tv.anime.ui.common.SpacerWidth
import com.seiko.tv.anime.ui.common.foundation.FocusableImageButton
import com.seiko.tv.anime.ui.common.foundation.FocusableTextButton
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
  tags: List<AnimeTag> = emptyList(),
  types: List<AnimeTag> = emptyList(),
  indexes: List<AnimeTag> = emptyList(),
  description: String = "",
  isFavorite: Boolean = false,
  onFavoriteClick: () -> Unit = {},
  onTagClick: (AnimeTag) -> Unit = {}
) {
  val (tagsFocus, btnStarFocus) = FocusRequester.createRefs()

  var parentIsFocused by remember { mutableStateOf(false) }

  Row(
    modifier = modifier
      .onFocusChanged { parentIsFocused = it.isFocused }
      .focusTarget()
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
        types = types,
        indexes = indexes,
        description = description,
        onTagClick = onTagClick,
        modifier = Modifier.focusRequester(tagsFocus).focusProperties {
          down = btnStarFocus
        }
      )
      FocusableImageButton(
        image = if (isFavorite) {
          Icons.Filled.Favorite
        } else {
          Icons.Filled.FavoriteBorder
        },
        onClick = {
          btnStarFocus.requestFocus()
          onFavoriteClick()
        },
        modifier = Modifier.focusRequester(btnStarFocus).focusProperties {
          up = tagsFocus
        }
      )
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

  if (parentIsFocused) {
    SideEffect {
      btnStarFocus.requestFocus()
    }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun DetailAnimeInfoDesc(
  title: String,
  releaseTime: String,
  state: String,
  tags: List<AnimeTag>,
  types: List<AnimeTag>,
  indexes: List<AnimeTag>,
  description: String,
  onTagClick: (AnimeTag) -> Unit,
  modifier: Modifier = Modifier
) {
  Text(title, style = MaterialTheme.typography.titleMedium)

  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(releaseTime, style = MaterialTheme.typography.bodyMedium)
    SpacerWidth(20.dp)
    Text(state, style = MaterialTheme.typography.bodySmall)
  }

  var parentIsFocused by remember { mutableStateOf(false) }
  var focusIndex by rememberSaveable { mutableStateOf(0) }

  val focusManager = LocalFocusManager.current

  LazyRow(
    modifier
      .onFocusChanged { parentIsFocused = it.isFocused }
      .onFocusDirection {
        when (it) {
          FocusDirection.Up,
          FocusDirection.Down -> focusManager.moveFocus(FocusDirection.Out)
        }
        false
      }
      .focusTarget()
  ) {
    itemsIndexed(tags + types + indexes) { index, tag ->
      val focusRequester = remember { FocusRequester() }

      FocusableTextButton(
        text = tag.title,
        onClick = {
          focusRequester.requestFocus()
          onTagClick(tag)
        },
        modifier = Modifier
          .onFocusChanged {
            if (it.isFocused) focusIndex = index
          }
          .focusRequester(focusRequester)
      )
      SpacerWidth(10.dp)

      if (parentIsFocused && focusIndex == index) {
        SideEffect {
          focusRequester.requestFocus()
        }
      }
    }
  }

  Text(
    description,
    style = MaterialTheme.typography.headlineSmall,
    overflow = TextOverflow.Ellipsis,
    maxLines = 5
  )
}

@Preview(widthDp = 1280, heightDp = 400)
@Composable
private fun DetailAnimeInfoPreview() {
  AnimeTvTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      DetailAnimeInfo(
        title = "魔法纪录 魔法少女小圆外传 第二季 -觉醒前夜-",
        releaseTime = "日本",
        state = "更新至第6集",
        tags = listOf("搞笑", "奇幻", "百合", "治愈").map { AnimeTag(it, "") },
        types = emptyList(),
        indexes = emptyList(),
        description = "《魔法纪录魔法少女小圆外传第二季-觉醒前夜-》TV动画《魔法少女小圆☆魔法少女小圆外传2ndSEASON-觉醒前夜-》的放送决定于7月31日（周六）24:00开始！在第二季SEASON放送之前，7月3日（周六）开始将连续3周播放第一季SEASON的总集篇，7月24日（周六）将播出第二季SEASON之前的特别节目！此外，FinalSeason-浅梦之晓预计将于2021年末播出！作为实现愿望的代价，在无人知晓的情况下不断战斗的魔法少女们。然而，环伊吕波，却忘记了自己的愿望。“成为魔法少女的时候，我是许了什么愿望来着？”日常当中突然出现的空洞。失去了的某件重要之物。连理由都不知道，就这样每天不停地战斗……就在此时，魔法少女之间开始流传某个传闻。“若能去神滨的话，魔法少女就能得到拯救”。魔法少女与传闻集中的城市，神滨市。寻求着自己失去的愿望，环伊吕波的故事开始了——"
      )
    }
  }
}

@Preview(widthDp = 1280, heightDp = 400)
@Composable
private fun DetailAnimeInfoV2Preview() {
  AnimeTvTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      DetailAnimeInfo(
        title = "境界触发者 第三季",
        releaseTime = "日本",
        state = "更新至第1集",
        tags = listOf("日语", "tv").map { AnimeTag(it, "") },
        types = emptyList(),
        indexes = emptyList(),
        description = "《境界触发者第三季》TV动画《境界触发者》第3季制作决定！"
      )
    }
  }
}
