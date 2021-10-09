package com.seiko.tv.anime.ui.composer.collector

import androidx.compose.runtime.Composable
import kotlin.reflect.KClass

/**
 * 收集Composable函数，自动生成Dagger Module
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class CollectCompose(
  val qualifier: KClass<out Any>
)

interface CollectComposeOwner<in T> {
  @Composable
  fun Show(scope: T)
}

@Composable
fun <T> T.Show(owners: Collection<CollectComposeOwner<T>>) {
  owners.forEach { owner -> owner.Show(this) }
}
