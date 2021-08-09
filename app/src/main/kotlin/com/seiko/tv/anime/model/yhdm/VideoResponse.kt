package com.seiko.tv.anime.model.yhdm

import com.seiko.tv.anime.model.serializer.PlayUrlStringSerializer
import moe.tlaster.hson.annotations.HtmlSerializable

data class VideoResponse(
  @HtmlSerializable(
    "div.bofang > div",
    attr = "data-vid",
    serializer = PlayUrlStringSerializer::class
  )
  val playUrl: String,
)