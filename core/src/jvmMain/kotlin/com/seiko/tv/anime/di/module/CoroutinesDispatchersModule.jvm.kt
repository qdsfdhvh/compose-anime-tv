package com.seiko.tv.anime.di.module

import com.seiko.tv.anime.di.scope.defaultDispatcher
import com.seiko.tv.anime.di.scope.ioDispatcher
import com.seiko.tv.anime.di.scope.mainDispatcher
import com.seiko.tv.anime.di.scope.mainImmediateDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

internal actual val coroutinesDispatchersModule = module {
  single(defaultDispatcher) { Dispatchers.Default }
  single(ioDispatcher) { Dispatchers.IO }
  single(mainDispatcher) { Dispatchers.Main }
  single(mainImmediateDispatcher) { Dispatchers.Main.immediate }
}
