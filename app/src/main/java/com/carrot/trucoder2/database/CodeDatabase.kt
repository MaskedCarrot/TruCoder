package com.carrot.trucoder2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.carrot.trucoder2.model.Leaderboard
import com.carrot.trucoder2.model.ResultContest

@Database(
    entities = [ResultContest::class , Leaderboard::class],
    version = 1
)
abstract class CodeDatabase : RoomDatabase(){

    abstract fun getResultContestDao():ResultContestDao
    abstract fun getLeaderboardDao():LeaderboardDao

    companion object{
        @Volatile
        private var instance: CodeDatabase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CodeDatabase::class.java,
                "trucoder.db"
            )
                .fallbackToDestructiveMigration()
                .build()



    }
}