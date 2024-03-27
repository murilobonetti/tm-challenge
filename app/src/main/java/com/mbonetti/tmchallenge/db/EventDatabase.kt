package com.mbonetti.tmchallenge.db

import androidx.room.Database
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

}
