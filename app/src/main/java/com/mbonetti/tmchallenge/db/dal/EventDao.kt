package com.mbonetti.tmchallenge.db.dal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mbonetti.tmchallenge.db.models.Event

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(event: Event)

    @Query("SELECT * FROM event")
    fun getAllEvents(): LiveData<List<Event>>

}
