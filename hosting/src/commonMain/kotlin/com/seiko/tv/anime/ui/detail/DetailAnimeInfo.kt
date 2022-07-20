package com.seiko.tv.anime.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.seiko.compose.focuskit.onFocusDirection
import com.seiko.tv.anime.model.anime.AnimeTag
import com.seiko.tv.anime.ui.theme.uiValue
import com.seiko.tv.anime.ui.widget.FocusableImageButton
import com.seiko.tv.anime.ui.widget.FocusableTextButton
import com.seiko.tv.anime.ui.widget.NetworkImage

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
  Text(title, style = MaterialTheme.typography.titleLarge)

  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(releaseTime, style = MaterialTheme.typography.bodyMedium)
    Spacer(Modifier.width(20.dp))
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
      Spacer(Modifier.width(10.dp))

      if (parentIsFocused && focusIndex == index) {
        SideEffect {
          focusRequester.requestFocus()
        }
      }
    }
  }

  Text(
    description,
    style = MaterialTheme.typography.labelSmall,
    overflow = TextOverflow.Ellipsis,
    maxLines = 5
  )
}
