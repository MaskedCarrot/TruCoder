package com.carrot.trucoder2.model

import com.google.gson.annotations.SerializedName

data class ResponseCodechef(
    @SerializedName("contest_ratings")
    val resultCodechefContest_ratings: List<RecentParticipation>,
    @SerializedName("contests")
    val resultCodechefContests: List<ResultCodechefContest>,
    val country_rank: Int,
    val global_rank: Int,
    val handle: String,
    val highest_rating: Int,
    val rating: Int,
    val stars: String,
    val status : String
)