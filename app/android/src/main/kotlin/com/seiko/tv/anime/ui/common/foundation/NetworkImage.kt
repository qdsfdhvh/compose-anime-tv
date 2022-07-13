package com.seiko.tv.anime.ui.common.foundation

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.seiko.imageloader.rememberAsyncImagePainter
import com.seiko.tv.anime.R

@Composable
fun NetworkImage(
  data: String,
  modifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Crop
) {
  val painter = rememberAsyncImagePainter(data)
  Image(
    painter = painter,
    modifier = modifier,
    contentScale = contentScale,
    contentDescription = stringResource(id = R.string.accessibility_common_network_image)
  )
}
