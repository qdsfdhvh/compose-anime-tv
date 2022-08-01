package com.seiko.tv.anime.ui.setting

import androidx.compose.runtime.Composable
import com.seiko.tv.anime.ui.widget.TvSelectDialog
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun SettingDialog(navigator: Navigator) {
  TvSelectDialog(
    title = "Setting",
    text = "just test dialog",
    onCenterClick = {
      navigator.goBack()
    },
    onCancelClick = {
      navigator.goBack()
    }
  )
}
