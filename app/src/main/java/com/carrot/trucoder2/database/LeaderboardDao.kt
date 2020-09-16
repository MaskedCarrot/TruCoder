package com.carrot.trucoder2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.carrot.trucoder2.model.Leaderboard
import com.carrot.trucoder2.model.ResponseLeaderboard

@Dao
interface LeaderboardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun InsertFriend(leaderboard: List<Leaderboard>)

    @Delete
    suspend fun DeleteFriend(leaderboard: Leaderboard)

    @Query("Select * From friendsLeaderboard WHERE platform = 2 ORDER BY rating DESC")
    fun getAllCCFriends(): LiveData<List<Leaderboard>>

    @Query("Select * From friendsLeaderboard WHERE platform = 1 ORDER BY rating DESC")
    fun getAllCFFriends(): LiveData<List<Leaderboard>>

    @Query("SELECT  Name FROM friendsLeaderboard WHERE platform = 2")
    suspend fun getAllFriendsCCHandle(): List<String>

    @Query("SELECT  Name FROM friendsLeaderboard WHERE platform = 1")
    suspend fun getAllFriendsCFHandle(): List<String>

    @Query("DELETE FROM friendsLeaderboard WHERE platform = 1")
    suspend fun deleteFromCF()

    @Query("DELETE FROM friendsLeaderboard WHERE platform = 2")
    suspend fun deleteFromCC()
}