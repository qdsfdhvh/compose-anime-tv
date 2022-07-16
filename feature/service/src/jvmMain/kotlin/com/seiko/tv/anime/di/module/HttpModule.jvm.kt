package com.seiko.tv.anime.di.module

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module

internal actual fun Module.setupHttpClientEngine() {
  single { OkHttp.create() }
}
