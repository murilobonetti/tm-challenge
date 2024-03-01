package com.mbonetti.tmchallenge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mbonetti.tmchallenge.db.dal.EventDao
import com.mbonetti.tmchallenge.db.models.Event
import com.mbonetti.tmchallenge.db.util.Converters

@Database(
    entities = [
        Event::class,
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class EventDatabase : RoomDatabase() {

    abstract fun getEventDao(): EventDao

    companion object {
        @Volatile
        private var instance: EventDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            EventDatabase::class.java,
            name = "event_database.db"
        ).build()
    }
}
