package com.seiko.tv.anime.di.assisted

import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ComposeAssistedFactoryKey(
  val value: KClass<out ComposeAssistedFactory>,
)