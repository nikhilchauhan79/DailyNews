package com.example.dailynews.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.dailynews.data.db.entities.SourceEntity

@Dao
interface SourceDao {
  @Query("select * from sourceentity")
  fun getAllSources(): List<SourceEntity>

  @Query("select * from sourceentity where articleID = :articleID")
  fun getSourcesByArticleID(articleID: String): SourceEntity

  @Query("select * from sourceentity where id =:id")
  fun getSourceByID(id: String) : SourceEntity?

  @Insert
  fun insertSource(sourceEntity: SourceEntity)

  @Insert
  fun insertSources(sourceEntities: List<SourceEntity>)

  @Transaction
  fun upsertSources(sources: List<SourceEntity>) {
    val freshSources = sources.filter {
      it.sourceID ?: return@filter true
      getSourceByID(it.sourceID) == null
    }

    insertSources(freshSources)
  }

  @Query("delete from sourceentity")
  fun deleteAllSources()
}