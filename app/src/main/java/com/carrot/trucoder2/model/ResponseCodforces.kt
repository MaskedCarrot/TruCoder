package com.carrot.trucoder2.model

import com.google.gson.annotations.SerializedName

data class ResponseCodforces(
    @SerializedName("contests")
    val resutsCodeforcesContests: List<RecentParticipation>,
    val lastSeen: String,
    val max_rank: String,
    val max_rating: Int,
    val photo: String,
    val rank: String,
    val rating: Int,
    val status :String
)