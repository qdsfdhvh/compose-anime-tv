package com.seiko.compose.player.internal

import java.util.concurrent.TimeUnit

internal fun getDurationString(durationMs: Long, negativePrefix: Boolean = false): String {
  val hours = TimeUnit.MILLISECONDS.toHours(durationMs)
  val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMs)
  val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMs)
  return if (hours > 0) {
    String.format(
      "%s%02d:%02d:%02d",
      if (negativePrefix) "-" else "",
      hours,
      minutes - TimeUnit.HOURS.toMinutes(hours),
      seconds - TimeUnit.MINUTES.toSeconds(minutes)
    )
  } else String.format(
    "%s%02d:%02d",
    if (negativePrefix) "-" else "",
    minutes,
    seconds - TimeUnit.MINUTES.toSeconds(minutes)
  )
}
