package com.example.dailynews.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailynews.data.db.entities.SourceXEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceXDao {
  @Query("select * from sourcexentity")
  fun getAllSourcesLD(): LiveData<List<SourceXEntity>>

  @Query("select * from sourcexentity")
  fun getAllSources(): Flow<List<SourceXEntity>>

  @Insert
  suspend fun insertAllSources(sources: List<SourceXEntity>)
}