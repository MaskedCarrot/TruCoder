package com.carrot.trucoder2.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecentParticipation(
    @SerializedName(value = "name" , alternate = ["contestName"])
    val event : String,
    val rank : String ,
    @SerializedName(value = "rating" , alternate = ["newRating"])
    val rating : Int
): Parcelable