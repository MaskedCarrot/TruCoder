package com.carrot.trucoder2.data.model

import com.squareup.moshi.Json

data class BackendContestContainer(
    @Json(name = "status") val status: String?,
    @Json(name = "contests") val contest: List<BackendContest>?
)
