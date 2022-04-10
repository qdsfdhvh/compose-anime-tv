package com.seiko.tv.anime.di.scope

import org.koin.core.qualifier.named

val defaultDispatcher = named("DefaultDispatcher")
val ioDispatcher = named("IoDispatcher")
val mainDispatcher = named("MainDispatcher")
val mainImmediateDispatcher = named("MainImmediateDispatcher")
