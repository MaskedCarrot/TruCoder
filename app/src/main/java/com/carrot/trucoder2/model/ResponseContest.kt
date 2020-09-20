package com.carrot.trucoder2.model

import com.google.gson.annotations.SerializedName

data class ResponseContest(
    @SerializedName("result")
    val resultContest: List<ResultContest>,
    val status :String
)