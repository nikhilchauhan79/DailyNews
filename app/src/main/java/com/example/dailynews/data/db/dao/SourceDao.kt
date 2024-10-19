package com.example.dailynews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailynews.data.db.entities.SourceEntity

@Dao
interface SourceDao {
  @Query("select * from sourceentity")
  fun getAllSources(): List<SourceEntity>

  @Query("select * from sourceentity where articleID = :articleID")
  fun getSourcesByArticleID(articleID: String): SourceEntity

  @Insert
  fun insertSource(sourceEntity: SourceEntity)

  @Insert
  fun insertSources(sourceEntities: List<SourceEntity>)

  @Query("delete from sourceentity")
  fun deleteAllSources()
}