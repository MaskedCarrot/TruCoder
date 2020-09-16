package com.carrot.trucoder2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "friendsLeaderboard")
@Parcelize
data class Leaderboard(
    @PrimaryKey
    @SerializedName(value = "Name"  , alternate = ["handle"])
    val Name : String,
    @SerializedName(value = "rank" , alternate = ["stars"])
    val rank :String,
    val rating:Int,
    val platform:Int ,
    val avatar : String
): Parcelable