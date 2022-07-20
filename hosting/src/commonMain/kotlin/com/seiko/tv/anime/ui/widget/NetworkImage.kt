package com.seiko.tv.anime.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
fun NetworkImage(
  data: String,
  modifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Crop,
  contentDescription: String? = null,
) {
  val painter = rememberAsyncImagePainter(data)
  Image(
    painter = painter,
    modifier = modifier,
    contentScale = contentScale,
    contentDescription = contentDescription,
  )
}
