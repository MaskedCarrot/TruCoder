package com.carrot.trucoder2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carrot.trucoder2.model.ResultContest

@Dao
interface ResultContestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun InsertinResultContest(list:List<ResultContest>)

    @Query("DELETE FROM ResultContest")
    suspend fun NukeinResultContest()

    @Query("SELECT * FROM ResultContest WHERE currentStatus = \"upcomming\" ORDER BY timestamp ASC ")
    fun getResultContest():LiveData<List<ResultContest>>

    @Query("SELECT * FROM ResultContest WHERE currentStatus = \"ongoing\"  ORDER BY timestamp ASC ")
    fun getResultRunningContest():LiveData<List<ResultContest>>

    @Query("SELECT * FROM ResultContest WHERE currentStatus  = \"ongoing\" AND id = 2 ORDER BY timestamp ASC ")
    fun getResultRunningCCContest():LiveData<List<ResultContest>>

    @Query("SELECT * FROM ResultContest WHERE currentStatus = \"ongoing\" AND id = 1 ORDER BY timestamp ASC ")
    fun getResultRunningCFContest():LiveData<List<ResultContest>>

    @Query("SELECT * FROM ResultContest WHERE currentStatus = \"ongoing\" AND id = 35 ORDER BY timestamp ASC ")
    fun getResultRunningGContest():LiveData<List<ResultContest>>

    @Query("SELECT * FROM ResultContest WHERE currentStatus = \"ongoing\" AND id = 12 ORDER BY timestamp ASC ")
    fun getResultRunningTContest():LiveData<List<ResultContest>>

    @Query("SELECT * FROM ResultContest WHERE id = 1 AND currentStatus = \"upcomming\" ORDER BY timestamp ASC ")
    fun getCodeforcesContests(): LiveData<List<ResultContest>>

    @Query("SELECT * FROM ResultContest WHERE id = 2 AND currentStatus = \"upcomming\" ORDER BY timestamp ASC")
    fun getCodechefContests(): LiveData<List<ResultContest>>

    @Query("SELECT * FROM ResultContest WHERE id = 35 AND currentStatus = \"upcomming\" ORDER BY timestamp ASC")
    fun getGoogleContests(): LiveData<List<ResultContest>>

    @Query("SELECT * FROM ResultContest WHERE id = 12 AND currentStatus = \"upcomming\" ORDER BY timestamp ASC")
    fun getTopcoderContests(): LiveData<List<ResultContest>>


}