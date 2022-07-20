package com.seiko.tv.anime.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.ui.theme.AnimeTvTheme
import com.seiko.tv.anime.ui.theme.backgroundColor

@Preview(showBackground = true)
@Composable
fun TvTabItemPreview() {
  AnimeTvTheme {
    Surface(color = backgroundColor) {
      Row(
        modifier = Modifier.padding(5.dp)
      ) {
        TvTabBarItem("首页", isFocused = true, isSelected = true)
        TvTabBarItem("日本动漫", isFocused = false, isSelected = true)
        TvTabBarItem("国产动漫", isFocused = false, isSelected = false)
      }
    }
  }
}
