package com.mbonetti.tmchallenge.di

import android.content.Context
import androidx.room.Room
import com.mbonetti.tmchallenge.db.dal.EventDao
import com.mbonetti.tmchallenge.db.EventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideEventDao(database: EventDatabase): EventDao {
        return database.getEventDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): EventDatabase {
        return Room.databaseBuilder(
            appContext,
            EventDatabase::class.java,
            "event_database.db"
        ).build()
    }
}
