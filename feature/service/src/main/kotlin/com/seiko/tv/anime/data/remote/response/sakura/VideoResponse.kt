package com.seiko.tv.anime.data.remote.response.sakura

import com.seiko.tv.anime.util.serializer.PlayUrlStringSerializer
import moe.tlaster.hson.annotations.HtmlSerializable

data class VideoResponse(
  @HtmlSerializable(
    "div.bofang > div",
    attr = "data-vid",
    serializer = PlayUrlStringSerializer::class
  )
  val playUrl: String,
)
