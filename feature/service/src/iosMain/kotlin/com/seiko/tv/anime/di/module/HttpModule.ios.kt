package com.seiko.tv.anime.di.module

import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module

internal actual fun Module.setupHttpClientEngine() {
  single { Darwin.create() }
}
