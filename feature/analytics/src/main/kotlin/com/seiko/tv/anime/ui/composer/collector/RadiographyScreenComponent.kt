package com.seiko.tv.anime.ui.composer.collector

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.insets.statusBarsPadding
import com.seiko.tv.anime.di.scope.CollectScreenComponentQualifier
import radiography.Radiography

@OptIn(ExperimentalComposeUiApi::class)
@CollectCompose(CollectScreenComponentQualifier::class)
@Composable
fun BoxScope.RadiographyScreenComponent() {
  var isShowDialog by remember { mutableStateOf(false) }

  Text(
    text = "Rendering",
    color = Color.Cyan,
    modifier = Modifier
      .align(Alignment.TopEnd)
      .statusBarsPadding()
      .padding(end = 10.dp)
      .border(1.dp, Color.Cyan, MaterialTheme.shapes.small)
      .padding(4.dp)
      .clickable { isShowDialog = true }
  )

  if (isShowDialog) {
    val rendering = remember { Radiography.scan() }
    Dialog(
      // @see https://stackoverflow.com/questions/65243956/jetpack-compose-fullscreen-dialog
      properties = DialogProperties(usePlatformDefaultWidth = false),
      onDismissRequest = { isShowDialog = false }
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth(0.8f)
          .fillMaxHeight(0.8f)
          .background(MaterialTheme.colors.background, RoundedCornerShape(6.dp))
          .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = rendering,
          style = MaterialTheme.typography.caption,
          modifier = Modifier
            .weight(1f)
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState())
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
          onClick = { isShowDialog = false },
        ) {
          Text("OK")
        }
      }
    }
  }
}
