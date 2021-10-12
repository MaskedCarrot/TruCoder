package com.carrot.trucoder2.data.model

import com.squareup.moshi.Json

data class BackendContest(
    @Json(name = "id") val id: Int?,
    @Json(name = "href") val href: String?,
    @Json(name = "event") val event: String?,
    @Json(name = "duration") val duration: Long?,
    @Json(name = "start_time") val statTime: Long?,
    @Json(name = "end_time") val endTime: Long?
)
