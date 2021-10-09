package com.seiko.tv.anime.di.scope

import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AssistedFactoryKey(
  val value: KClass<out Any>,
)
