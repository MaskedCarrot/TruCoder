package com.carrot.trucoder2.repository

import com.carrot.trucoder2.database.CodeDatabase
import com.carrot.trucoder2.model.Leaderboard
import com.carrot.trucoder2.model.ResponseLeaderboard
import com.carrot.trucoder2.model.ResultContest
import com.carrot.trucoder2.retrofit.RetrofitService

class CodeRespository(val db :CodeDatabase ) {
    suspend fun fetchCodeforcesUser(user : String) =
        RetrofitService.api.fetchCodeforcesUser(user)

    suspend fun fetchCodechefUser(user : String) =
        RetrofitService.api.fetchCodechefUser(user)

    suspend fun fetchFriendListCF(handles:String) =
        RetrofitService.api.fetchFriendsListCF(handles)

    suspend fun fetchFriendListCC(handles: String) =
        RetrofitService.api.fetchFriendListCC(handles)

    suspend fun fetchContests() =
        RetrofitService.api.fetchContest()


    suspend fun NukeContests()=
        db.getResultContestDao().NukeinResultContest()

    suspend fun InsertContests(list : List<ResultContest>) =
        db.getResultContestDao().InsertinResultContest(list)

    suspend fun InsertFriends(leaderboard: ResponseLeaderboard) =
        db.getLeaderboardDao().InsertFriend(leaderboard.result)

    suspend fun DeleteFriends(leaderboard: Leaderboard) =
        db.getLeaderboardDao().DeleteFriend(leaderboard)

    fun getCodechefContests() =
        db.getResultContestDao().getCodechefContests()

    fun getCodeforcesContests() =
        db.getResultContestDao().getCodeforcesContests()

    fun getGoogleContests()=
        db.getResultContestDao().getGoogleContests()

    fun getTopcoderContest()=
        db.getResultContestDao().getTopcoderContests()

    fun getAllContests() =
        db.getResultContestDao().getResultContest()

    fun getAllCCFriends() =
        db.getLeaderboardDao().getAllCCFriends()

    fun getAllCFFriends() =
        db.getLeaderboardDao().getAllCFFriends()

    suspend fun RefreshCC()=
        db.getLeaderboardDao().getAllFriendsCCHandle()
    suspend fun RefreshCF() =
        db.getLeaderboardDao().getAllFriendsCFHandle()


    fun getAllRunningContest()=
        db.getResultContestDao().getResultRunningContest()
    fun getAllRunningCCContest()=
        db.getResultContestDao().getResultRunningCCContest()
    fun getAllRunningCFContest()=
        db.getResultContestDao().getResultRunningCFContest()
    fun getAllRunningGContest()=
        db.getResultContestDao().getResultRunningGContest()
    fun getAllRunningTContest()=
        db.getResultContestDao().getResultRunningTContest()


    suspend fun deleteAllCC()=
        db.getLeaderboardDao().deleteFromCC()

    suspend fun deleteAllCF()=
        db.getLeaderboardDao().deleteFromCF()




}