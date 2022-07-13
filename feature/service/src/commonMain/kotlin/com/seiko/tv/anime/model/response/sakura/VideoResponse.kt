package com.seiko.tv.anime.model.response.sakura

import com.seiko.tv.anime.util.serializer.PlayUrlStringSerializer
import moe.tlaster.hson.annotations.HtmlSerializable

internal data class VideoResponse(
  @HtmlSerializable(
    "div.bofang > div",
    attr = "data-vid",
    serializer = PlayUrlStringSerializer::class
  )
  val playUrl: String,
)
