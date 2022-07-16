package dev.chrisbanes.snapper

import io.github.aakira.napier.Napier

private const val DebugLog = false

internal object SnapperLog {
  inline fun d(tag: String = "SnapperFlingBehavior", noinline message: () -> String) {
    if (DebugLog) {
      Napier.d(tag = tag, message = message)
    }
  }
}
