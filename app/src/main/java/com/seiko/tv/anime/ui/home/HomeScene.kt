package com.seiko.tv.anime.ui.home

import androidx.compose.animation.core.animateIntSizeAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seiko.tv.anime.model.Anime
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.widget.foundation.NetworkImage

@Composable
fun HomeScene() {
  val viewModel: HomeViewModel = hiltViewModel()

  val list by viewModel.animeList.collectAsState(emptyList())

  Box(Modifier.fillMaxSize()) {
    LazyRow(
      modifier = Modifier
        .align(Alignment.Center)
    ) {
      itemsIndexed(list) { index, anime ->
        AnimeCard(index, anime)
      }
    }
  }
}

@Composable
private fun AnimeCard(index: Int, anime: Anime) {
  val focusRequester = remember { FocusRequester() }
  var isFocused by remember { mutableStateOf(index == 0) }

  val size by animateIntSizeAsState(
    if (isFocused) IntSize(160, 240) else IntSize(140, 200),
  )

  Box(
    modifier = Modifier
      .onFocusChanged { isFocused = it.isFocused }
      .focusRequester(focusRequester)
      .focusTarget()
      .clickable { focusRequester.requestFocus() }
      .size(160.dp, 280.dp),
  ) {
    Column(
      modifier = Modifier.align(Alignment.Center)
    ) {
      NetworkImage(
        data = anime.imageUrl,
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .size(size.width.dp, size.height.dp)
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = anime.name,
        modifier = Modifier
          .width(140.dp)
          .align(Alignment.CenterHorizontally),
        color = if (isFocused) Color.White else Color.Black,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        maxLines = 1,
      )
    }
  }

  DisposableEffect(Unit) {
    if (index == 0) focusRequester.requestFocus()
    onDispose { }
  }
}

@Preview
@Composable
fun PreviewAnimeCardFocused() {
  AnimeTvTheme {
    AnimeCard(
      index = 0,
      anime = Anime(
        id = 12241,
        name = "干支魂 猫客万来",
        imageUrl = "https://ddcdn-img.acplay.net/anime/12241.jpg!client"
      )
    )
  }
}

@Preview
@Composable
fun PreviewAnimeCard() {
  AnimeTvTheme {
    AnimeCard(
      index = 1,
      anime = Anime(
        id = 12241,
        name = "干支魂 猫客万来",
        imageUrl = "https://ddcdn-img.acplay.net/anime/12241.jpg!client"
      )
    )
  }
}