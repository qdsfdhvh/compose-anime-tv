package com.seiko.tv.anime

import androidx.compose.runtime.staticCompositionLocalOf
import com.seiko.tv.anime.di.assisted.AssistedFactoryMap
import com.seiko.tv.anime.navigation.AppNavigator

val LocalAppNavigator = staticCompositionLocalOf<AppNavigator> { error("No Navigator") }
val LocalAssistedFactoryMap = staticCompositionLocalOf<AssistedFactoryMap> { emptyMap() }
