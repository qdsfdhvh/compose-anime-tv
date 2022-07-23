package com.seiko.tv.anime.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.R

/**
 * 生成 TV Banner
 * Split -> Copy Image
 */
@Composable
fun AppBanner() {
  Box(
    modifier = Modifier
      .size(160.dp, 80.dp)
      .background(Color.White)
  ) {
    val image = ImageVector.vectorResource(R.drawable.ic_play_station_48dp)
    Icon(
      painter = rememberVectorPainter(image = image),
      contentDescription = null,
      modifier = Modifier.align(Alignment.Center)
    )
  }
}

@Preview
@Composable
fun AppBannerPreview() {
  AppBanner()
}
