package com.seiko.tv.anime.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiko.tv.anime.ui.theme.AnimeTvTheme

@Preview
@Composable
fun TvSelectDialogButtonPreview() {
  AnimeTvTheme {
    Row {
      TvSelectDialogButton(text = "确认", isFocused = true, onClick = {})
      Spacer(modifier = Modifier.width(5.dp))
      TvSelectDialogButton(text = "取消", isFocused = false, onClick = {})
    }
  }
}
