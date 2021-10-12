package com.carrot.trucoder2.data.model

import com.squareup.moshi.Json

data class BackendBasicDetailsContainer(
    @Json(name = "status") val status: String?,
    @Json(name = "basic_details") val basicDetails: BackendBasicDetails?
)
