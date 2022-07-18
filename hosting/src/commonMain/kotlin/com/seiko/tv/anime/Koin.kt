package com.seiko.tv.anime

import com.seiko.tv.anime.di.appModules
import com.seiko.tv.anime.di.commonModules
import com.seiko.tv.anime.di.serviceModules
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun startAppKoin(appDeclaration: KoinAppDeclaration) = startKoin {
  appDeclaration.invoke(this)
  modules(commonModules + serviceModules + appModules)
}
