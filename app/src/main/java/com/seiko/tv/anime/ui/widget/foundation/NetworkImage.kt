package com.seiko.tv.anime.ui.widget.foundation

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.LoadPainter
import com.seiko.tv.anime.R

@Composable
fun NetworkImage(
  data: Any,
  modifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Crop,
  placeholder: @Composable (() -> Unit)? = null,
) {
  val painter = if (data is Painter) {
    data
  } else {
    rememberCoilPainter(request = data, fadeIn = true)
  }
  if (painter is LoadPainter<*> && painter.loadState is ImageLoadState.Loading) {
    placeholder?.invoke()
  }
  Image(
    painter = painter,
    modifier = modifier,
    contentScale = contentScale,
    contentDescription = stringResource(id = R.string.accessibility_common_network_image)
  )
}