package com.carrot.trucoder2.data.model

import com.squareup.moshi.Json

data class BackendBasicDetails(
    @Json(name = "handle") val handle: String?,
    @Json(name = "avatar") val avatar: String?,
    @Json(name = "level") val level: String?,
    @Json(name = "rating") val rating: Int?
)
