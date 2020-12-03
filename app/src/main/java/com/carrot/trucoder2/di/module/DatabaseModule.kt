package com.carrot.trucoder2.di.module

import android.content.Context
import androidx.room.Room
import com.carrot.trucoder2.database.CodeDatabase
import com.carrot.trucoder2.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCodeDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            CodeDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideLeaderBoardDao(database: CodeDatabase) =
        database.getLeaderboardDao()

    @Singleton
    @Provides
    fun provideResultDao(database: CodeDatabase) =
        database.getResultContestDao()

}