package com.seiko.tv.anime

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily.Companion.Monospace
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.tv.anime.ui.theme.ComposeanimetvTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    @Suppress("DEPRECATION")
    window.setFlags(
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    )

    setContent {
      ComposeanimetvTheme {
        MainContent()
      }
    }
  }
}

@Composable
fun MainContent() {
  Column(
    Modifier
      .background(Color(0xFFEDEAE0))
      .fillMaxSize()
      .padding(32.dp),
    verticalArrangement = Arrangement.spacedBy(24.dp)
  ) {
    var textState by remember { mutableStateOf(TextFieldValue()) }
    var focusState by remember { mutableStateOf("") }

    val localFocusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    Text(
      text = focusState,
      fontSize = 22.sp,
      fontFamily = Monospace,
      color = Color(0xFF0047AB)
    )

    OutlinedTextField(
      value = textState,
      onValueChange = { textState = it },
      label = { Text(text = "Input your name") },
      modifier = Modifier
        .focusRequester(focusRequester)
        .fillMaxWidth()
        .onFocusChanged {
          focusState = if (it.isFocused) {
            "TextField is focused."
          } else {
            "TextField has no focus."
          }
        },
    )

    Button(onClick = {
      focusRequester.requestFocus()
    }) {
      Text(text = "Request Focus")
    }

    Button(onClick = {
      localFocusManager.clearFocus()
    }) {
      Text(text = "Clear Focus")
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  ComposeanimetvTheme {
    MainContent()
  }
}